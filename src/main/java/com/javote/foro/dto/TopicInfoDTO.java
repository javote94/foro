package com.javote.foro.dto;

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
