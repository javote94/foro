package com.aluracursos.forohub.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthUserDTO(

        @NotBlank
        String email,

        @NotBlank
        String password
) {}
