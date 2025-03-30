package com.javote.foro.validation;

import com.javote.foro.entity.Response;
import com.javote.foro.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResponseDeleteValidatorExecutor {

    private final List<ResponseDeleteValidator> validators;

    public void validate(User user, Response response) {
        validators.stream()
                .filter(v -> v.appliesTo().equals(user.getProfile()))
                .forEach(v -> v.validate(user,response));
    }

}
