package com.javote.foro.validation;

import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;
import com.javote.foro.enums.Profile;
import com.javote.foro.exception.UnauthorizedStudentException;
import org.springframework.stereotype.Component;

@Component
public class StudentTopicDeleteValidator implements TopicDeleteValidator{

    @Override
    public Profile appliesTo() {
        return Profile.USER;
    }

    @Override
    public void validate(User user, Topic topic) {
        if (user.getProfile() == Profile.USER &&
            !user.getId().equals(topic.getAuthor().getId())) {
            throw new UnauthorizedStudentException("You can only delete your own topics.");
        }
    }
}
