package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Detailed information about a response.")
public record ResponseInfoDTO(

        @Schema(description = "Response ID", example = "42")
        Long id,

        @Schema(description = "Message content", example = "Try checking the filter chain in your security configuration.")
        String message,

        @Schema(description = "ID of the response author", example = "3")
        Long authorId,

        @Schema(description = "Full name of the author", example = "Carlos Lopez")
        String authorName,

        @Schema(description = "Topic ID to which the response belongs", example = "18")
        Long topicId,

        @Schema(description = "Date and time the response was created")
        LocalDateTime creationDate,

        @Schema(description = "Whether the response is marked as the solution", example = "false")
        Boolean solution

) {}
