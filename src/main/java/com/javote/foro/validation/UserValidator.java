package com.javote.foro.validation;

import com.javote.foro.dto.SaveUserDTO;

public interface UserValidator {

    void validate(SaveUserDTO saveUserDTO);
}
