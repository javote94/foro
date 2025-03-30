package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Data to update the title or message of a topic.")
public record UpdateTopicDTO(

        @Schema(description = "New title for the topic", example = "Updated topic title")
        @Size(min = 10, message = "Title must be at least 10 characters long")
        String title,

        @Schema(description = "New message content", example = "Updated explanation or content.")
        @Size(min = 10, message = "Message must be at least 10 characters long")
        String message
) {}
