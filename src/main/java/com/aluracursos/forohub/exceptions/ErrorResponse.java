package com.aluracursos.forohub.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

// Clase para estructurar la respuesta de error que le llega al cliente con un formato limpio.
@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int statusCode;
    private String error;
    private List<ValidationErrorDetails> errors;
    private String path;

    public ErrorResponse(int statusCode, String error, List<ValidationErrorDetails> errors, String path) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.error = error;
        this.errors = errors;
        this.path = path;
    }

}
