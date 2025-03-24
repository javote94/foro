package com.javote.foro.validation;


import com.javote.foro.entity.Course;
import com.javote.foro.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseAccessValidatorExecutor {

    private final List<CourseAccessValidator> validators;

    public void validate(User user, Course course) {
        validators.forEach(validator -> validator.validate(user, course));
    }

}
