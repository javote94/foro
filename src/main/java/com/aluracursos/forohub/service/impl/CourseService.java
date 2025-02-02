package com.aluracursos.forohub.service.impl;

import com.aluracursos.forohub.dto.CourseInfoDTO;
import com.aluracursos.forohub.dto.SaveCourseDTO;
import com.aluracursos.forohub.entity.Course;
import com.aluracursos.forohub.entity.Topic;
import com.aluracursos.forohub.entity.User;
import com.aluracursos.forohub.exception.UserNotFoundException;
import com.aluracursos.forohub.repository.CourseRepository;
import com.aluracursos.forohub.repository.UserRepository;
import com.aluracursos.forohub.service.ICourseService;
import com.aluracursos.forohub.util.CourseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CourseInfoDTO createCourse(SaveCourseDTO saveCourseDTO) {

        User moderator = userRepository.findById(Long.valueOf(saveCourseDTO.moderatorId()))
                .orElseThrow(() -> new UserNotFoundException("Moderator not found with ID: " + saveCourseDTO.moderatorId()));

        Course course = Course.builder()
                .name(saveCourseDTO.name())
                .moderator(moderator)
                .active(true)
                .build();

        return CourseMapper.toDto(courseRepository.save(course));
    }

    @Override
    public Boolean isModeratorOfCourse(Topic topic, User authenticatedUser) {
        return courseRepository.existsByTopicIdAndModeratorId(topic.getId(), authenticatedUser.getId());
    }


}
