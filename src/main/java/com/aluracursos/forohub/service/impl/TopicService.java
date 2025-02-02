package com.aluracursos.forohub.service.impl;

import com.aluracursos.forohub.dto.*;
import com.aluracursos.forohub.enums.Profile;
import com.aluracursos.forohub.enums.Status;
import com.aluracursos.forohub.exception.CourseNotFoundException;
import com.aluracursos.forohub.exception.TopicNotFoundException;
import com.aluracursos.forohub.exception.UnauthorizedModeratorException;
import com.aluracursos.forohub.entity.Course;
import com.aluracursos.forohub.entity.Topic;
import com.aluracursos.forohub.entity.User;
import com.aluracursos.forohub.util.TopicMapper;
import com.aluracursos.forohub.repository.CourseRepository;
import com.aluracursos.forohub.repository.TopicRepository;
import com.aluracursos.forohub.service.ICourseService;
import com.aluracursos.forohub.service.ITopicService;
import com.aluracursos.forohub.util.AuthenticatedUserProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TopicService implements ITopicService {

    private final ICourseService courseService;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public TopicInfoDTO createTopic(SaveTopicDTO saveTopicDTO) {

        User author = authenticatedUserProvider.getAuthenticatedUser();

        Course course = courseRepository.findById(Long.valueOf(saveTopicDTO.courseId()))
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + saveTopicDTO.courseId()));

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
    public Page<TopicInfoDTO> listTopics(String courseName, Integer year, Pageable pageable) {

        Page<Topic> topicPage;
        if (courseName != null && year != null) {
            topicPage = topicRepository.findByCourseNameAndYear(courseName, year, pageable);
        } else if (courseName != null) {
            topicPage = topicRepository.findByCourseName(courseName, pageable);
        } else if (year != null) {
            topicPage = topicRepository.findByYear(year, pageable);
        } else {
            topicPage = topicRepository.findByActiveTrue(pageable);
        }
        return topicPage.map(TopicMapper::toDto);
    }

    @Override
    public TopicInfoDTO getTopicById(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));
        return TopicMapper.toDto(topic);
    }

    @Override
    @Transactional
    public TopicInfoDTO changeTopicStatus(Long topicId, UpdateTopicStatusDTO updateTopicStatusDTO) {

        // Obtener el tópico en base al id
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));

        // Validar si el usuario autenticado es moderador del curso al que pertenece el tópico
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        if (authenticatedUser.getProfile() == Profile.MODERATOR &&
            !courseService.isModeratorOfCourse(topic, authenticatedUser)) {
            throw new UnauthorizedModeratorException("You are not allowed to moderate this topic.");
        }

        topic.setStatus(updateTopicStatusDTO.status());

        // No es necesario llamar a topicRepository.save(topic) aquí
        return TopicMapper.toDto(topic);
    }

    @Override
    @Transactional
    public TopicInfoDTO changeTopicActiveStatus(Long topicId, UpdateTopicActiveDTO updateTopicActiveDTO) {

        // Obtener el tópico en base al id
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));

        // Validar si el usuario autenticado es moderador del curso al que pertenece el tópico
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        if (authenticatedUser.getProfile() == Profile.MODERATOR &&
            !courseService.isModeratorOfCourse(topic, authenticatedUser)) {
            throw new UnauthorizedModeratorException("You are not allowed to moderate this topic.");
        }

        topic.setActive(updateTopicActiveDTO.active());
        return TopicMapper.toDto(topic);
    }

    @Override
    @Transactional
    public TopicInfoDTO updateTopicContent(Long topicId, UpdateTopicContentDTO updateTopicContentDTO) {

        // Obtener el tópico en base al id
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));

        // Validar que el usuario autenticado sea el autor del tópico
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        if (!topic.getAuthor().getId().equals(authenticatedUser.getId())) {
            throw new AccessDeniedException("You can only modify your own topics.");
        }

        // Actualizar título y/o mensaje
        if (updateTopicContentDTO.title() != null) {
            if (updateTopicContentDTO.title().isBlank() || updateTopicContentDTO.title().length() < 10) {
                throw new IllegalArgumentException("Title must be at least 10 characters long and cannot be blank");
            }
            topic.setTitle(updateTopicContentDTO.title());
        }
        if (updateTopicContentDTO.message() != null) {
            if (updateTopicContentDTO.message().isBlank() || updateTopicContentDTO.message().length() < 10) {
                throw new IllegalArgumentException("Message must be at least 10 characters long and cannot be blank");
            }
            topic.setMessage(updateTopicContentDTO.message());
        }

        return TopicMapper.toDto(topic);
    }


    @Override
    @Transactional
    public void deleteTopic(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));
        topic.setActive(false);
    }

}
