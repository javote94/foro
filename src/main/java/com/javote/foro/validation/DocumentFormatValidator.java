package com.javote.foro.validation;

import com.javote.foro.dto.SaveUserDTO;
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
