package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dto.CourseInfoDTO;
import com.aluracursos.forohub.dto.SaveCourseDTO;
import com.aluracursos.forohub.entity.Topic;
import com.aluracursos.forohub.entity.User;

public interface ICourseService {
    CourseInfoDTO createCourse(SaveCourseDTO saveCourseDTO);
    Boolean isModeratorOfCourse(Topic topic, User authenticatedUser);
}
