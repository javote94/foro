package com.javote.foro.validation;

import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;
import com.javote.foro.enums.Profile;

public interface TopicDeleteValidator {
    Profile appliesTo();
    void validate(User user, Topic topic);
}
