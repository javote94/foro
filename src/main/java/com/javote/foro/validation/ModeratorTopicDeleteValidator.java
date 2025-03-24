package com.javote.foro.validation;

import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;
import com.javote.foro.enums.Profile;
import com.javote.foro.exception.UnauthorizedModeratorException;
import com.javote.foro.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModeratorTopicDeleteValidator implements TopicDeleteValidator{

    private final CourseRepository courseRepository;

    @Override
    public Profile appliesTo() {
        return Profile.MODERATOR;
    }

    @Override
    public void validate(User user, Topic topic) {
        if (user.getProfile() == Profile.MODERATOR &&
            !courseRepository.isModeratorOfCourse(user.getId(), topic.getCourse().getId())) {
            throw new UnauthorizedModeratorException("You are not allowed to delete this topic.");
        }
    }
}
