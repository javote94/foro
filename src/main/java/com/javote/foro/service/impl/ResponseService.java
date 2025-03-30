package com.javote.foro.service.impl;

import com.javote.foro.dto.ResponseInfoDTO;
import com.javote.foro.dto.SaveResponseDTO;
import com.javote.foro.entity.Course;
import com.javote.foro.enums.Profile;
import com.javote.foro.enums.Status;
import com.javote.foro.exception.*;
import com.javote.foro.entity.Response;
import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;
import com.javote.foro.repository.CourseRepository;
import com.javote.foro.repository.ResponseRepository;
import com.javote.foro.repository.TopicRepository;
import com.javote.foro.service.IResponseService;
import com.javote.foro.util.AuthenticatedUserProvider;
import com.javote.foro.util.ResponseMapper;
import com.javote.foro.validation.CourseAccessValidatorExecutor;
import com.javote.foro.validation.ResponseDeleteValidatorExecutor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService implements IResponseService {

    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final CourseAccessValidatorExecutor accessValidatorExecutor;
    private final ResponseDeleteValidatorExecutor responseDeleteValidatorExecutor;

    @Override
    @Transactional
    public ResponseInfoDTO createResponse(Long topicId, SaveResponseDTO saveResponseDTO) {

        User author = authenticatedUserProvider.getAuthenticatedUser();

        Topic topic = topicRepository.findByIdAndActiveTrue(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found with id: " + topicId));

        Course course = topic.getCourse();

        accessValidatorExecutor.validate(author, course);

        Response response = Response.builder()
                .message(saveResponseDTO.message())
                .topic(topic)
                .author(author)
                .creationDate(LocalDateTime.now())
                .active(true)
                .solution(false)
                .build();

        return ResponseMapper.toDto(responseRepository.save(response));
    }

    @Override
    @Transactional
    public ResponseInfoDTO toggleSolutionStatus(Long responseId) {

        User user = authenticatedUserProvider.getAuthenticatedUser();

        Response response = responseRepository.findByIdAndActiveTrue(responseId)
                .orElseThrow(() -> new ResponseNotFoundException("Response with id " + responseId + " not found"));

        Topic topic = response.getTopic();
        Course course = topic.getCourse();

        if (user.getProfile() == Profile.USER &&
            !user.getId().equals(topic.getAuthor().getId())) {
            throw new UnauthorizedStudentException("You are not allowed to mark this response as a solution.");
        }

        if (user.getProfile() == Profile.MODERATOR &&
            !courseRepository.isModeratorOfCourse(user.getId(), course.getId())) {
            throw new UnauthorizedModeratorException("You are not allowed to mark this response as a solution.");
        }

        if (response.getSolution()) {
            // Si la respuesta estaba marcada como solución -> desmarcarla
            response.setSolution(false);
            responseRepository.save(response);

            // Verificar si existía otras soluciones para definir el estado del tópico
            boolean hasOtherSolutions = responseRepository.existsByTopicIdAndSolutionTrue(topic.getId());
            if (!hasOtherSolutions) {
                topic.setStatus(Status.UNSOLVED);
            }

        } else {
            boolean existsAnotherSolutions = responseRepository.existsAnotherSolutionInTopic(topic.getId(), response.getId());
            if (existsAnotherSolutions) {
                throw new AnotherSolutionAlreadyExistsException("There is already a solution marked for this topic.");
            }
            response.setSolution(true);
            responseRepository.save(response);
            topic.setStatus(Status.RESOLVED);
        }

        topicRepository.save(topic);
        return ResponseMapper.toDto(response);
    }

    @Override
    @Transactional
    public void deleteResponse(Long responseId) {

        Response response = responseRepository.findByIdAndActiveTrue(responseId)
                .orElseThrow(() -> new ResponseNotFoundException("Response with id " + responseId + " not found"));

        User user = authenticatedUserProvider.getAuthenticatedUser();

        responseDeleteValidatorExecutor.validate(user, response);

        response.setActive(false);

        // Si esta respuesta era la única solución, desmarcar el estado del tópico
        if (response.getSolution()) {
            Topic topic = response.getTopic();
            topic.setStatus(Status.UNSOLVED);
        }
    }
}
