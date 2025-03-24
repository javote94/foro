package com.javote.foro.validation;

import com.javote.foro.entity.Course;
import com.javote.foro.entity.User;
import com.javote.foro.enums.Profile;
import com.javote.foro.exception.UnauthorizedModeratorException;
import com.javote.foro.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModeratorCourseAccessValidator implements CourseAccessValidator {

    private final CourseRepository courseRepository;

    @Override
    public void validate(User user, Course course) {
        if(user.getProfile() == Profile.MODERATOR &&
           !courseRepository.isModeratorOfCourse(user.getId(),course.getId())){
            throw new UnauthorizedModeratorException("You are not allowed to create a topic in this course.");
        }
    }
}
