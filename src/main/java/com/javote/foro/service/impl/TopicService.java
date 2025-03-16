package com.javote.foro.service.impl;

import com.javote.foro.dto.TopicInfoDTO;
import com.javote.foro.dto.UpdateTopicContentDTO;
import com.javote.foro.dto.UpdateTopicStatusDTO;
import com.javote.foro.enums.Profile;
import com.javote.foro.enums.Status;
import com.javote.foro.exception.CourseNotFoundException;
import com.javote.foro.exception.TopicNotFoundException;
import com.javote.foro.exception.UnauthorizedModeratorException;
import com.javote.foro.entity.Course;
import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;
import com.javote.foro.exception.UnauthorizedStudentException;
import com.javote.foro.util.TopicMapper;
import com.javote.foro.repository.CourseRepository;
import com.javote.foro.repository.TopicRepository;
import com.javote.foro.service.ICourseService;
import com.javote.foro.service.ITopicService;
import com.javote.foro.util.AuthenticatedUserProvider;
import com.javote.foro.dto.SaveTopicDTO;
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

        if(author.getProfile() == Profile.USER &&
           !courseRepository.isAStudentOfTheCourse(author.getId(),course.getId())){
            throw new UnauthorizedStudentException("You are not allowed to create a topic in this course.");
        }

        if(author.getProfile() == Profile.MODERATOR &&
           !courseRepository.isModeratorOfCourse(author.getId(),course.getId())){
            throw new UnauthorizedModeratorException("You are not allowed to create a topic in this course.");
        }

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

        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        Page<Topic> topicPage;

        // Caso 1: Si el usuario proporciona un `courseId`
        if (courseId != null) {
            // Verificar que existe el curso con el id dado
            if (!courseRepository.findById(courseId).isPresent()) {
                throw new CourseNotFoundException("Course not found with ID: " + courseId);
            }
            // Si el usuario es USER
            if (authenticatedUser.getProfile() == Profile.USER) {
                // Verifica que el usuario esté inscripto al curso dado
                if (courseRepository.isAStudentOfTheCourse(authenticatedUser.getId(), courseId)) {
                    topicPage = topicRepository.findTopicsByCourseId(courseId, pageable);
                } else {
                    throw new UnauthorizedStudentException("You are not allowed to view the topics for this course.");
                }
            // Si el usuario es MODERATOR
            } else if (authenticatedUser.getProfile() == Profile.MODERATOR){
                // Verifica que el usuario sea moderador del curso dado
                if (courseRepository.isModeratorOfCourse(authenticatedUser.getId(), courseId)) {
                    topicPage = topicRepository.findTopicsByCourseId(courseId, pageable);
                } else {
                    throw new UnauthorizedModeratorException("You are not allowed to view the topics for this course.");
                }
            // Si el usuario es ADMIN (no tiene restricciones)
            } else if (authenticatedUser.getProfile() == Profile.ADMIN) {
                topicPage = topicRepository.findTopicsByCourseId(courseId, pageable);
            } else {
                throw new RuntimeException("An unexpected error has occurred.");
            }
        // Caso 2: Si el usuario no proporciona un `courseId`
        } else {
            if (authenticatedUser.getProfile() == Profile.USER) {
                topicPage = topicRepository.findTopicsByEnrolledCourses(authenticatedUser.getId(), pageable);
            } else if (authenticatedUser.getProfile() == Profile.MODERATOR) {
                topicPage = topicRepository.findTopicsByModeratedCourses(authenticatedUser.getId(), pageable);
            } else if (authenticatedUser.getProfile() == Profile.ADMIN) {
                topicPage = topicRepository.findByActiveTrue(pageable);
            } else {
                throw new RuntimeException("No tienes acceso a esta información");
            }
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
    public TopicInfoDTO updateTopicContent(Long topicId, UpdateTopicContentDTO updateTopicContentDTO) {

        // Obtener el tópico en base al id
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));

        // Validar que el usuario autenticado sea el autor del tópico
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        if (!topic.getAuthor().getId().equals(authenticatedUser.getId())) {
            throw new UnauthorizedStudentException("You can only modify your own topics.");
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

        // Obtener el tópico en base al id
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));

        // Validar si el usuario autenticado es moderador del curso al que pertenece el tópico
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        if (authenticatedUser.getProfile() == Profile.MODERATOR &&
            !courseService.isModeratorOfCourse(topic, authenticatedUser)) {
            throw new UnauthorizedModeratorException("You are not allowed to moderate this topic.");
        }

        topic.setActive(false);
    }

}
