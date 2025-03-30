package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data required to respond to a topic.")
public record SaveResponseDTO(

        @Schema(description = "Message content of the response", example = "Try checking the filter chain in your security configuration.")
        @NotBlank(message = "Message is mandatory")
        String message

) {
}
