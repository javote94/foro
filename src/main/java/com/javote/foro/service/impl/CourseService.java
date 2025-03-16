package com.javote.foro.service.impl;

import com.javote.foro.dto.CourseInfoDTO;
import com.javote.foro.dto.SaveCourseDTO;
import com.javote.foro.dto.UpdateCourseDTO;
import com.javote.foro.entity.Course;
import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;
import com.javote.foro.enums.Profile;
import com.javote.foro.exception.CourseNotFoundException;
import com.javote.foro.exception.ModeratorNotFoundException;
import com.javote.foro.exception.UnauthorizedModeratorException;
import com.javote.foro.exception.UserNotFoundException;
import com.javote.foro.repository.CourseRepository;
import com.javote.foro.repository.UserRepository;
import com.javote.foro.service.ICourseService;
import com.javote.foro.util.AuthenticatedUserProvider;
import com.javote.foro.util.CourseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

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

        User moderator = authenticatedUserProvider.getAuthenticatedUser();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));

        if (moderator.getProfile() == Profile.MODERATOR &&
            !courseRepository.isModeratorOfCourse(moderator.getId(), course.getId())) {
            throw new UnauthorizedModeratorException("You are not allowed to add students to this course");
        }

        User student = userRepository.findStudentById(Long.valueOf(updateCourseDTO.studentId()))
                .orElseThrow(() -> new UserNotFoundException("Student with id " + updateCourseDTO.studentId() + " not found"));

        if(!course.getStudents().contains(student)) {
            course.getStudents().add(student);
            student.getCourses().add(course);
            courseRepository.save(course);
        }

        return CourseMapper.toDto(course);
    }

    @Override
    public void deleteCourse(Long courseId) {

        // Obtener el curso en base al id
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));

        course.setActive(false);
    }

    @Override
    public Boolean isModeratorOfCourse(Topic topic, User authenticatedUser) {
        return courseRepository.existsByTopicIdAndModeratorId(topic.getId(), authenticatedUser.getId());
    }

}
