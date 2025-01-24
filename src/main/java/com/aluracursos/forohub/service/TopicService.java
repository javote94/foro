package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.*;
import com.aluracursos.forohub.enums.Profile;
import com.aluracursos.forohub.exceptions.TopicNotFoundException;
import com.aluracursos.forohub.exceptions.UnauthorizedModeratorException;
import com.aluracursos.forohub.model.Course;
import com.aluracursos.forohub.model.Topic;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.CourseRepository;
import com.aluracursos.forohub.repository.TopicRepository;
import com.aluracursos.forohub.repository.UserRepository;
import com.aluracursos.forohub.utils.AuthenticatedUserProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
public class TopicService implements ITopicService{

    private final ICourseService courseService;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public TopicService(ICourseService courseService, AuthenticatedUserProvider authenticatedUserProvider,
                        TopicRepository topicRepository, UserRepository userRepository,
                        CourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    @Transactional
    public TopicInfoDTO createTopic(SaveTopicDTO saveTopicDTO) {
        User author = userRepository.getReferenceById(Long.valueOf(saveTopicDTO.idAuthor()));
        Course course = courseRepository.findByName(saveTopicDTO.courseName())
                .orElseThrow(() -> new RuntimeException("There is no course with the given name"));
        Topic topic = topicRepository.save(
                new Topic(saveTopicDTO.title(), saveTopicDTO.message(), author, course)
        );
        return new TopicInfoDTO(topic);
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
        return topicPage.map(TopicInfoDTO::new);
    }

    @Override
    public TopicInfoDTO getTopicById(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));
        return new TopicInfoDTO(topic);
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
        return new TopicInfoDTO(topic);
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
        return new TopicInfoDTO(topic);
    }

    @Override
    @Transactional
    public TopicInfoDTO updateTopicContent(Long topicId, UpdateTopicContentDTO updateTopicContentDTO) {

        // Obtener el tópico en base al id
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));

        // Validar que el usuario autenticado sea el autor
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

        return new TopicInfoDTO(topic);
    }


    @Override
    @Transactional
    public void deleteTopic(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));
        topic.setActive(false);
    }

}
