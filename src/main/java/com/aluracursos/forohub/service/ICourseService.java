package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.CourseInfoDTO;
import com.aluracursos.forohub.dtos.SaveCourseDTO;
import com.aluracursos.forohub.model.Course;
import com.aluracursos.forohub.model.Topic;
import com.aluracursos.forohub.model.User;

public interface ICourseService {
    CourseInfoDTO create(SaveCourseDTO saveCourseDTO);
    Boolean isModeratorOfCourse(Topic topic, User authenticatedUser);
}
