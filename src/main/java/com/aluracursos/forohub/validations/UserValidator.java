package com.aluracursos.forohub.validations;

import com.aluracursos.forohub.dtos.SaveUserDTO;

public interface UserValidator {

    void validate(SaveUserDTO saveUserDTO);
}
