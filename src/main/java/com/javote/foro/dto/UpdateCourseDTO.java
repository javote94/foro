package com.javote.foro.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCourseDTO(

        @NotBlank
        String studentId
) {}
