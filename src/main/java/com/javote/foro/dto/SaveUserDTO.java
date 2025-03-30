package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Data required to register a new student user.")
public record SaveUserDTO(

    @Schema(description = "First name of the user", example = "John")
    @NotBlank
    String name,

    @Schema(description = "Last name of the user", example = "Doe")
    @NotBlank
    String lastName,

    @Schema(description = "National document number (6 to 8 digits)", example = "34567891")
    @NotBlank
    @Pattern(regexp = "\\d{6,8}")
    String document,

    @Schema(description = "Email address", example = "johndoe@mail.com")
    @NotBlank
    @Email
    String email,

    @Schema(description = "Raw password to be encrypted", example = "securePass123")
    @NotBlank
    String password

) {}
