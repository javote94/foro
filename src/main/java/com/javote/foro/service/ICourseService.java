package com.javote.foro.service;

import com.javote.foro.dto.CourseInfoDTO;
import com.javote.foro.dto.SaveCourseDTO;
import com.javote.foro.dto.UpdateCourseDTO;
import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;

public interface ICourseService {
    CourseInfoDTO createCourse(SaveCourseDTO saveCourseDTO);
    CourseInfoDTO addStudent(Long courseId, UpdateCourseDTO updateCourseDTO);
    Boolean isModeratorOfCourse(Topic topic, User authenticatedUser);
    void deleteCourse(Long courseId);
}
