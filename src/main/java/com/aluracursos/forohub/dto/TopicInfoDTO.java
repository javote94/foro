package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.entity.Topic;
import java.time.LocalDateTime;

public record TopicInfoDTO(
    Long id,
    String title,
    String message,
    Long authorId,
    Long courseId,
    LocalDateTime creationDate,
    String status
) {}
