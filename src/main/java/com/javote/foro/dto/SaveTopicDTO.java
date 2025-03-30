package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Data required to create a new topic.")
public record SaveTopicDTO(

    @Schema(description = "ID of the course where the topic will be created", example = "4")
    @NotBlank(message = "Course ID is mandatory")
    String courseId,

    @Schema(description = "Title of the topic", example = "How does Spring Security work?")
    @NotBlank(message = "Title is mandatory")
    @Size(min = 10, message = "Title must be at least 10 characters long")
    String title,

    @Schema(description = "Content or message of the topic", example = "I'm having trouble understanding how filters are applied.")
    @NotBlank(message = "Message is mandatory")
    @Size(min = 10, message = "Message must be at least 10 characters long")
    String message

) {}
