package com.aluracursos.forohub.dtos;

import jakarta.validation.constraints.NotBlank;

public record SaveCourseDTO(

       @NotBlank
       String name

) {}
