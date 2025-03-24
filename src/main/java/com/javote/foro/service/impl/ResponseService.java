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
import com.javote.foro.validation.CourseAccessValidator;
import com.javote.foro.validation.CourseAccessValidatorExecutor;
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

    @Override
    @Transactional
    public ResponseInfoDTO createResponse(Long topicId, SaveResponseDTO saveResponseDTO) {

        User author = authenticatedUserProvider.getAuthenticatedUser();

        Topic topic = topicRepository.findById(topicId)
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
    public ResponseInfoDTO markResponseAsSolution(Long responseId) {

        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();

        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new ResponseNotFoundException("Response with id " + responseId + " not found"));

        Topic topic = response.getTopic();

        Course course = topic.getCourse();

        if (authenticatedUser.getProfile() == Profile.USER) {
            if (!authenticatedUser.getId().equals(topic.getAuthor().getId())) {
                throw new UnauthorizedStudentException("You are not allowed to mark this response as a solution.");
            }
        } else if (authenticatedUser.getProfile() == Profile.MODERATOR) {
            if (!courseRepository.isModeratorOfCourse(authenticatedUser.getId(), course.getId())) {
                throw new UnauthorizedModeratorException("You are not allowed to mark this response as a solution.");
            }
        }

        response.setSolution(true);
        responseRepository.save(response);

        topic.setStatus(Status.RESOLVED);
        topicRepository.save(topic);

        return ResponseMapper.toDto(response);
    }
}
