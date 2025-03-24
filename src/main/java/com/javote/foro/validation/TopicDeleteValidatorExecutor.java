package com.javote.foro.validation;

import com.javote.foro.entity.Topic;
import com.javote.foro.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TopicDeleteValidatorExecutor {

    private final List<TopicDeleteValidator> validators;

    public void validate(User user, Topic topic) {
        validators.stream()
                .filter(v -> v.appliesTo().equals(user.getProfile()))
                .forEach(v -> v.validate(user, topic));

    }

}
