package com.javote.foro.util;

import com.javote.foro.dto.CourseInfoDTO;
import com.javote.foro.entity.Course;

public class CourseMapper {

    public static CourseInfoDTO toDto(Course course){
        return new CourseInfoDTO(
                course.getId(),
                course.getName(),
                course.getModerator().getId()
        );
    }

}
