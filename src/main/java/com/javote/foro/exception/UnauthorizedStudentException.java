package com.javote.foro.exception;

public class UnauthorizedStudentException extends RuntimeException{
    public UnauthorizedStudentException(String message) {
        super(message);
    }
}
