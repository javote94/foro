package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Data required to register a new moderator user.")
public record SaveModeratorDTO(

        @Schema(description = "First name of the moderator", example = "Ana")
        @NotBlank
        String name,

        @Schema(description = "Last name of the moderator", example = "Gomez")
        @NotBlank
        String lastName,

        @Schema(description = "National document number (6 to 8 digits)", example = "35678910")
        @NotBlank
        @Pattern(regexp = "\\d{6,8}")
        String document,

        @Schema(description = "Email address", example = "anagomez@mail.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "Password in plain text", example = "Moderator123")
        @NotBlank
        String password
) {}
