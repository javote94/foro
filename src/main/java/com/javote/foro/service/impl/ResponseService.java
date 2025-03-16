package com.javote.foro.service.impl;

import com.javote.foro.dto.ResponseInfoDTO;
import com.javote.foro.dto.SaveResponseDTO;
import com.javote.foro.entity.Course;
import com.javote.foro.enums.Profile;
import com.javote.foro.enums.Status;
import com.javote.foro.exception.ResponseNotFoundException;
import com.javote.foro.exception.TopicNotFoundException;
import com.javote.foro.entity.Response;
import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;
import com.javote.foro.exception.UnauthorizedModeratorException;
import com.javote.foro.exception.UnauthorizedStudentException;
import com.javote.foro.repository.CourseRepository;
import com.javote.foro.repository.ResponseRepository;
import com.javote.foro.repository.TopicRepository;
import com.javote.foro.service.IResponseService;
import com.javote.foro.util.AuthenticatedUserProvider;
import com.javote.foro.util.ResponseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResponseService implements IResponseService {

    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    @Transactional
    public ResponseInfoDTO createResponse(Long topicId, SaveResponseDTO saveResponseDTO) {

        User author = authenticatedUserProvider.getAuthenticatedUser();

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found with id: " + topicId));

        Course course = topic.getCourse();

        // Validaciones de permisos según el tipo de usuario
        if (author.getProfile() == Profile.USER) {
            if (!courseRepository.isAStudentOfTheCourse(author.getId(), course.getId())) {
                throw new UnauthorizedStudentException("You are not allowed to respond to this topic because you are not enrolled in the course.");
            }
        } else if (author.getProfile() == Profile.MODERATOR) {
            if (!courseRepository.isModeratorOfCourse(author.getId(), course.getId())) {
                throw new UnauthorizedModeratorException("You are not allowed to respond to this topic because you are not the moderator of the course.");
            }
        }

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
    public ResponseInfoDTO markResponseAsSolution(Long responseId) {

        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();

        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new ResponseNotFoundException("Response with id " + responseId + " not found"));

        Topic topic = response.getTopic();

        Course course = topic.getCourse();

        // Validar que el usuario sea el autor del tópico, un moderador del curso o un admin
        if (authenticatedUser.getProfile() == Profile.USER) {
            if (!authenticatedUser.getId().equals(topic.getAuthor().getId())) {
                throw new UnauthorizedStudentException("You are not allowed to mark this response as a solution.");
            }
        } else if (authenticatedUser.getProfile() == Profile.MODERATOR) {
            if (!courseRepository.isModeratorOfCourse(authenticatedUser.getId(), course.getId())) {
                throw new UnauthorizedModeratorException("You are not allowed to mark this response as a solution.");
            }
        }

        // Marcar la respuesta como solución
        response.setSolution(true);
        responseRepository.save(response);

        // Cambiar el estado del tópico a RESOLVED automáticamente
        topic.setStatus(Status.RESOLVED);
        topicRepository.save(topic);

        return ResponseMapper.toDto(response);

    }



}
