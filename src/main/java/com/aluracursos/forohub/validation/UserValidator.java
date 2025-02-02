package com.aluracursos.forohub.validation;

import com.aluracursos.forohub.dto.SaveUserDTO;

public interface UserValidator {

    void validate(SaveUserDTO saveUserDTO);
}
