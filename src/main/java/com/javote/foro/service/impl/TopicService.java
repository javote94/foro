package com.javote.foro.service.impl;

import com.javote.foro.dto.TopicInfoDTO;
import com.javote.foro.dto.UpdateTopicDTO;
import com.javote.foro.enums.Profile;
import com.javote.foro.enums.Status;
import com.javote.foro.exception.*;
import com.javote.foro.entity.Course;
import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;
import com.javote.foro.util.TopicMapper;
import com.javote.foro.repository.CourseRepository;
import com.javote.foro.repository.TopicRepository;
import com.javote.foro.service.ITopicService;
import com.javote.foro.util.AuthenticatedUserProvider;
import com.javote.foro.dto.SaveTopicDTO;
import com.javote.foro.validation.CourseAccessValidatorExecutor;
import com.javote.foro.validation.TopicDeleteValidatorExecutor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TopicService implements ITopicService {

    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final CourseAccessValidatorExecutor accessValidatorExecutor;
    private final TopicDeleteValidatorExecutor topicDeleteValidatorExecutor;


    @Override
    @Transactional
    public TopicInfoDTO createTopic(SaveTopicDTO saveTopicDTO) {

        User author = authenticatedUserProvider.getAuthenticatedUser();

        Course course = courseRepository.findById(Long.valueOf(saveTopicDTO.courseId()))
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + saveTopicDTO.courseId()));

        accessValidatorExecutor.validate(author, course);

        Topic topic = Topic.builder()
                .title(saveTopicDTO.title())
                .message(saveTopicDTO.message())
                .author(author)
                .course(course)
                .creationDate(LocalDateTime.now())
                .status(Status.UNSOLVED)
                .active(true)
                .build();

        return TopicMapper.toDto(topicRepository.save(topic));
    }

    @Override
    public Page<TopicInfoDTO> listTopics(Long courseId, Pageable pageable) {

        Page<Topic> topics;
        User user = authenticatedUserProvider.getAuthenticatedUser();

        // Caso 1: Si el usuario proporciona un `courseId`
        if (courseId != null) {
            Course course = courseRepository.findById(Long.valueOf(courseId))
                    .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));

            accessValidatorExecutor.validate(user, course);

            topics = topicRepository.findByCourseIdAndActiveTrue(course.getId(), pageable);

        // Caso 2: Si el usuario no proporciona un `courseId`
        } else {
            topics = switch (user.getProfile()) {
                case USER -> topicRepository.findByCourse_Students_IdAndActiveTrue(user.getId(), pageable);
                case MODERATOR -> topicRepository.findByCourse_Moderator_IdAndActiveTrue(user.getId(), pageable);
                case ADMIN -> topicRepository.findByActiveTrue(pageable);
            };
        }
        return topics.map(TopicMapper::toDto);
    }

    @Override
    public TopicInfoDTO getTopicById(Long topicId) {

        User user = authenticatedUserProvider.getAuthenticatedUser();

        Topic topic = topicRepository.findByIdAndActiveTrue(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));

        Course course = topic.getCourse();

        accessValidatorExecutor.validate(user, course);

        return TopicMapper.toDto(topic);
    }

    @Override
    @Transactional
    public TopicInfoDTO updateTopic(Long topicId, UpdateTopicDTO updateTopicDTO) {

        Topic topic = topicRepository.findByIdAndActiveTrue(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));

        User user = authenticatedUserProvider.getAuthenticatedUser();

        if (!topic.getAuthor().getId().equals(user.getId())) {
            throw new UnauthorizedAuthorException("You can only modify your own topics.");
        }

        // Actualizar título y/o mensaje
        if (updateTopicDTO.title() != null) {
            if (updateTopicDTO.title().isBlank() || updateTopicDTO.title().length() < 10) {
                throw new IllegalArgumentException("Title must be at least 10 characters long and cannot be blank");
            }
            topic.setTitle(updateTopicDTO.title());
        }
        if (updateTopicDTO.message() != null) {
            if (updateTopicDTO.message().isBlank() || updateTopicDTO.message().length() < 10) {
                throw new IllegalArgumentException("Message must be at least 10 characters long and cannot be blank");
            }
            topic.setMessage(updateTopicDTO.message());
        }

        return TopicMapper.toDto(topic);
    }

    @Override
    @Transactional
    public void deleteTopic(Long topicId) {

        Topic topic = topicRepository.findByIdAndActiveTrue(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));

        User user = authenticatedUserProvider.getAuthenticatedUser();
        Course course = topic.getCourse();

        topicDeleteValidatorExecutor.validate(user, topic);

        topic.setActive(false);
        // Baja lógica de las respuestas asociadas
        if (topic.getResponses() != null) {
            topic.getResponses().forEach(response -> response.setActive(false));
        }
    }
}
