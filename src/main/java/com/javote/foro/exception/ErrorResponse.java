package com.javote.foro.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

// Clase para estructurar las respuestas de error
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ErrorResponse {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private int statusCode;
    private String error;
    @Builder.Default
    private List<ValidationErrorDetails> errors = Collections.emptyList();
    private String path;

}
