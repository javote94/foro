package com.aluracursos.forohub.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

// La clase proporciona un manejo global de excepciones para todos los controladores en la aplicación.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Esta excepción se lanza cuando un argumento anotado con @Valid falla la validación de Bean Validation.
    // Ocurre típicamente en controladores REST cuando los datos de entrada no cumplen con las restricciones
    // de validación definidas.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Obtener la lista de FieldErrors
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        // Se convierte cada FieldError en una instancia de ValidationErrorDetails.
        List<ValidationErrorDetails> errors = fieldErrors.stream()
                .map(ValidationErrorDetails::new)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                errors,
                request.getRequestURI() // obtener la ruta del request actual y agregarla a la respuesta de error
        );

        // Se construye una respuesta 400 Bad Request.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Esta excepción se lanza cuando el tópico buscado por el cliente no se encuentra en la base de datos
    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<String> handleTopicNotFoundException(TopicNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Esta excepción se lanza cuando no se encuentra un controlador para la URL solicitada
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
    }

    // Esta excepción se lanza si el cliente actualiza a un valor no válido para el status del tópico
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid value for status");
    }

    // Esta excepción se lanza si el cliente actualiza el valor de title o message de un tópico
    // con menos de 10 caracteres
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Esta excepción se lanza cuando el cliente envía una solicitud de autenticación con credenciales inválidas
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<String> handleJwtCreationException(JWTCreationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(JwtInvalidException.class)
    public ResponseEntity<String> handleJwtInvalidException(JwtInvalidException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // Captura y maneja cualquier otra excepción no específica.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Se construye una respuesta 500 Internal Server Error.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }


}
