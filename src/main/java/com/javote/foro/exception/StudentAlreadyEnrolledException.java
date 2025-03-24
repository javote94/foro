package com.javote.foro.exception;

public class StudentAlreadyEnrolledException extends RuntimeException {

    public StudentAlreadyEnrolledException(String message) {
        super(message);
    }
}
