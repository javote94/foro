package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.CourseInfoDTO;
import com.aluracursos.forohub.dtos.SaveCourseDTO;
import com.aluracursos.forohub.model.Course;
import com.aluracursos.forohub.model.Topic;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService implements ICourseService{

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public CourseInfoDTO create(SaveCourseDTO saveCourseDTO) {
        Course course = courseRepository.save(new Course(saveCourseDTO));
        return new CourseInfoDTO(
                course.getId(),
                course.getName()
        );
    }

    @Override
    public Boolean isModeratorOfCourse(Topic topic, User authenticatedUser) {
        return courseRepository.existsByTopicIdAndModeratorId(topic.getId(), authenticatedUser.getId());
    }


}
