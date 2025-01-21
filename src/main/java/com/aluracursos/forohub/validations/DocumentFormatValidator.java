package com.aluracursos.forohub.validations;

import com.aluracursos.forohub.dtos.SaveUserDTO;
import org.springframework.stereotype.Component;

@Component
public class DocumentFormatValidator implements UserValidator {

    @Override
    public void validate(SaveUserDTO saveUserDTO) {
        if (!saveUserDTO.document().matches("\\d{6,8}")) {
            throw new IllegalArgumentException("The document must have between 6 and 8 digits.");
        }
    }
}
