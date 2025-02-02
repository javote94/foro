package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record SaveCourseDTO(

       @NotBlank
       String name,

       @NotBlank(message = "Moderator ID is mandatory")
       String moderatorId

) {}
