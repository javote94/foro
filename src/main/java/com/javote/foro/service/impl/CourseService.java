package com.javote.foro.service.impl;

import com.javote.foro.dto.CourseInfoDTO;
import com.javote.foro.dto.SaveCourseDTO;
import com.javote.foro.dto.UpdateCourseDTO;
import com.javote.foro.entity.Course;
import com.javote.foro.entity.User;
import com.javote.foro.exception.*;
import com.javote.foro.repository.CourseRepository;
import com.javote.foro.repository.UserRepository;
import com.javote.foro.service.ICourseService;
import com.javote.foro.util.AuthenticatedUserProvider;
import com.javote.foro.util.CourseMapper;
import com.javote.foro.validation.CourseAccessValidatorExecutor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final CourseAccessValidatorExecutor accessValidatorExecutor;

    @Override
    @Transactional
    public CourseInfoDTO createCourse(SaveCourseDTO saveCourseDTO) {

        User moderator = userRepository.findModeratorById(Long.valueOf(saveCourseDTO.moderatorId()))
                .orElseThrow(() -> new ModeratorNotFoundException("Moderator not found with ID: " + saveCourseDTO.moderatorId()));

        Course course = Course.builder()
                .name(saveCourseDTO.name())
                .moderator(moderator)
                .active(true)
                .build();

        return CourseMapper.toDto(courseRepository.save(course));
    }

    @Override
    @Transactional
    public CourseInfoDTO addStudent(Long courseId, UpdateCourseDTO updateCourseDTO) {

        Course course = courseRepository.findByIdAndActiveTrue(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));

        User user = authenticatedUserProvider.getAuthenticatedUser();

        accessValidatorExecutor.validate(user, course);

        User student = userRepository.findStudentById(Long.valueOf(updateCourseDTO.studentId()))
                .orElseThrow(() -> new UserNotFoundException("Student with id " + updateCourseDTO.studentId() + " not found"));

        if(!course.getStudents().contains(student)) {
            course.getStudents().add(student);
            student.getCourses().add(course);
            courseRepository.save(course);
        } else {
            throw new StudentAlreadyEnrolledException("The student is already enrolled in the course");
        }

        return CourseMapper.toDto(course);
    }

    @Override
    public void deleteCourse(Long courseId) {

        Course course = courseRepository.findByIdAndActiveTrue(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));

        // Desactivar todos los tópicos del curso
        course.getTopics().forEach(topic -> {
            topic.setActive(false);
            // Desactivar todas las respuestas del tópico
            if (topic.getResponses() != null) {
                topic.getResponses().forEach(response -> response.setActive(false));
            }
        });
        // Finalmente, desactivar el curso
        course.setActive(false);
    }
}
