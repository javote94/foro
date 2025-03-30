package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credentials required for authentication.")
public record AuthUserDTO(

        @Schema(description = "User email", example = "johndoe@mail.com")
        @NotBlank
        String email,

        @Schema(description = "User password", example = "SecurePass123")
        @NotBlank
        String password
) {}
