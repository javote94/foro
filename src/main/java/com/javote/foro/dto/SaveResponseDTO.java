package com.javote.foro.dto;

import jakarta.validation.constraints.NotBlank;

public record SaveResponseDTO(

        @NotBlank(message = "Message is mandatory")
        String message

) {
}
