package com.javote.foro.util;

import com.javote.foro.dto.CourseInfoDTO;
import com.javote.foro.dto.UserInfoDTO;
import com.javote.foro.entity.Course;
import com.javote.foro.entity.User;

import java.util.List;

public class CourseMapper {

    public static CourseInfoDTO toDto(Course course){

        List<UserInfoDTO> userDTOs = course.getStudents().stream()
                .filter(User::getActive)  // Solo estudiantes activos
                .map(UserMapper::toDto)
                .toList();

        return new CourseInfoDTO(
                course.getId(),
                course.getName(),
                course.getModerator().getId(),
                userDTOs
        );
    }

}
