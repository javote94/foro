package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data required to enroll a student in a course.")
public record UpdateCourseDTO(

        @Schema(description = "ID of the student to be added", example = "11")
        @NotBlank
        String studentId
) {}
