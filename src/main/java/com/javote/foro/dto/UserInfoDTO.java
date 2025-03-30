package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data returned after user registration.")
public record UserInfoDTO(

        @Schema(description = "User ID", example = "12")
        Long id,

        @Schema(description = "First name", example = "John")
        String name,

        @Schema(description = "Last name", example = "Doe")
        String lastName,

        @Schema(description = "Document number", example = "34567891")
        String document,

        @Schema(description = "Email", example = "johndoe@mail.com")
        String email
) {}
