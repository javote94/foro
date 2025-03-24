package com.javote.foro.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TopicInfoDTO(
    Long id,
    String title,
    String message,
    Long authorId,
    String authorName,
    Long courseId,
    LocalDateTime creationDate,
    String status,
    List<ResponseInfoDTO> responses
) {}
