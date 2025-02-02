package com.aluracursos.forohub.util;

import com.aluracursos.forohub.dto.CourseInfoDTO;
import com.aluracursos.forohub.entity.Course;

public class CourseMapper {

    public static CourseInfoDTO toDto(Course course){
        return new CourseInfoDTO(
                course.getId(),
                course.getName(),
                course.getModerator().getId()
        );
    }

}
