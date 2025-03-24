package com.javote.foro.validation;

import com.javote.foro.entity.Course;
import com.javote.foro.entity.User;

public interface CourseAccessValidator {

    void validate(User user, Course course);

}
