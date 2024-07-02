package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.CourseInfoDTO;
import com.aluracursos.forohub.dtos.SaveCourseDTO;
import com.aluracursos.forohub.model.Course;
import com.aluracursos.forohub.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService implements ICourseService{

    @Autowired
    private CourseRepository repository;

    @Override
    @Transactional
    public CourseInfoDTO create(SaveCourseDTO saveCourseDTO) {
        Course course = repository.save(new Course(saveCourseDTO));
        return new CourseInfoDTO(
                course.getId(),
                course.getName()
        );
    }
}
