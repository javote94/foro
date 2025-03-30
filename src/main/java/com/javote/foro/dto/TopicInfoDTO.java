package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Detailed information of a topic, including its responses.")
public record TopicInfoDTO(

    @Schema(description = "Topic ID", example = "18")
    Long id,

    @Schema(description = "Title of the topic", example = "How does Spring Security work?")
    String title,

    @Schema(description = "Message content", example = "I'm having trouble understanding how filters are applied.")
    String message,

    @Schema(description = "ID of the topic author", example = "6")
    Long authorId,

    @Schema(description = "Full name of the author", example = "Ana Gomez")
    String authorName,

    @Schema(description = "Course ID", example = "2")
    Long courseId,

    @Schema(description = "Date and time the topic was created")
    LocalDateTime creationDate,

    @Schema(description = "Topic status", example = "UNSOLVED")
    String status,

    @Schema(description = "List of responses associated with the topic")
    List<ResponseInfoDTO> responses
) {}
