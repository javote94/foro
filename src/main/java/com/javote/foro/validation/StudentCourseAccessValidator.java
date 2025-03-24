package com.javote.foro.validation;

import com.javote.foro.entity.Course;
import com.javote.foro.entity.User;
import com.javote.foro.enums.Profile;
import com.javote.foro.exception.UnauthorizedStudentException;
import com.javote.foro.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentCourseAccessValidator implements CourseAccessValidator {

    private final CourseRepository courseRepository;

    @Override
    public void validate(User user, Course course) {

        if(user.getProfile() == Profile.USER &&
           !courseRepository.isAStudentOfTheCourse(user.getId(),course.getId())){
            throw new UnauthorizedStudentException("You do not have permission to perform the requested action");
        }

    }
}
