package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data required to create a new course.")
public record SaveCourseDTO(

       @Schema(description = "Name of the course", example = "Spring Boot Fundamentals")
       @NotBlank
       String name,

       @Schema(description = "ID of the moderator assigned to this course", example = "5")
       @NotBlank(message = "Moderator ID is mandatory")
       String moderatorId

) {}
