package com.javote.foro.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.List;
import java.util.stream.Collectors;

// Manejo global de excepciones para toda la API
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de validaciones en @RequestBody y @Valid (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Obtener la lista de FieldErrors
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        // Se convierte cada FieldError en una instancia de ValidationErrorDetails.
        List<ValidationErrorDetails> errors = fieldErrors.stream()
                .map(ValidationErrorDetails::new)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error("Validation error")
                        .errors(errors)
                        .path(request.getRequestURI())
                        .build()
        );
    }

    // Manejo de autenticación fallida (401 Unauthorized)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
            UsernameNotFoundException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    // Manejo de acceso denegado (403 Forbidden)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    // Manejo de recursos no encontrados (404 Not Found)
    @ExceptionHandler({NoHandlerFoundException.class, UserNotFoundException.class,
            CourseNotFoundException.class, ModeratorNotFoundException.class,
            TopicNotFoundException.class, ResponseNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(
            RuntimeException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(JwtInvalidException.class)
    public ResponseEntity<ErrorResponse> handleJwtInvalidException(
            JwtInvalidException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(UnauthorizedModeratorException .class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedModeratorException(
            UnauthorizedModeratorException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(UnauthorizedStudentException .class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedStudentException(
            UnauthorizedStudentException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }


    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ErrorResponse> handleJwtCreationException(
            JWTCreationException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    // Manejo de valores inválidos en JSON (400 Bad Request)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    // Esta excepción se lanza si el cliente actualiza el valor de title o message de un tópico
    // con menos de 10 caracteres
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }


    // Esta excepción se lanza si intenta inscribir un estudiante a un curso en el que ya está inscripto
    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    public ResponseEntity<ErrorResponse> handleStudentAlreadyEnrolledException(
            StudentAlreadyEnrolledException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }


    // Manejo de cualquier otra excepción inesperada (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

}
