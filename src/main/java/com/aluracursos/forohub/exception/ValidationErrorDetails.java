package com.aluracursos.forohub.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class ValidationErrorDetails {

    private String field;
    private String message;

    public ValidationErrorDetails(FieldError error) {
        this.field = error.getField();
        this.message = error.getDefaultMessage();
    }

}
