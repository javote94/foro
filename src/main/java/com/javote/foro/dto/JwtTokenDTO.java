package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT token returned after successful authentication.")
public record JwtTokenDTO(

        @Schema(description = "JSON Web Token for authenticated requests")
        String jwtToken
) {}
