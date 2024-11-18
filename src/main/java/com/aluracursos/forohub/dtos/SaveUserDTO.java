package com.aluracursos.forohub.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SaveUserDTO(

    @NotBlank
    String name,

    @NotBlank
    String lastName,

    @NotBlank
    @Pattern(regexp = "\\d{6,8}")
    String document,

    @NotBlank
    @Email
    String email,

    @NotBlank
    String password

) {}
