package com.javote.foro.dto;

import java.time.LocalDateTime;

public record ResponseInfoDTO(

        Long id,
        String message,
        Long idAuthor,
        Long idTopic,
        String topicStatus,
        LocalDateTime creationDate,
        Boolean solution

) {}
