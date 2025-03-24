package com.javote.foro.dto;

import java.time.LocalDateTime;

public record ResponseInfoDTO(

        Long id,
        String message,
        Long authorId,
        String authorName,
        Long topicId,
        LocalDateTime creationDate,
        Boolean solution

) {}
