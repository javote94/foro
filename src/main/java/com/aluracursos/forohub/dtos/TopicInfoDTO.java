package com.aluracursos.forohub.dtos;

import com.aluracursos.forohub.model.Topic;
import java.time.LocalDateTime;

public record TopicInfoDTO(
    Long id,
    String title,
    String message,
    String courseName,
    Long idAuthor,
    LocalDateTime creationDate,
    String status
) {
    public TopicInfoDTO(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCourse().getName(),
                topic.getAuthor().getId(),
                topic.getCreationDate(),
                topic.getStatus().toString()
        );
    }
}
