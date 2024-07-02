package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.CourseInfoDTO;
import com.aluracursos.forohub.dtos.SaveCourseDTO;

public interface ICourseService {
    CourseInfoDTO create(SaveCourseDTO saveCourseDTO);
}
