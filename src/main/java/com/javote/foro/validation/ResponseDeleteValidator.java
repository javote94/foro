package com.javote.foro.validation;

import com.javote.foro.entity.Response;
import com.javote.foro.entity.User;
import com.javote.foro.enums.Profile;

public interface ResponseDeleteValidator {
    Profile appliesTo();
    void validate(User user, Response response);
}
