package com.javote.foro.validation;

import com.javote.foro.entity.Response;
import com.javote.foro.entity.User;
import com.javote.foro.enums.Profile;
import com.javote.foro.exception.UnauthorizedStudentException;
import org.springframework.stereotype.Component;

@Component
public class StudentResponseDeleteValidator implements ResponseDeleteValidator{

    @Override
    public Profile appliesTo() {
        return Profile.USER;
    }

    @Override
    public void validate(User user, Response response) {
        if (user.getProfile() == Profile.USER &&
            !user.getId().equals(response.getAuthor().getId())) {
            throw new UnauthorizedStudentException("You can only delete your own responses.");
        }
    }
}
