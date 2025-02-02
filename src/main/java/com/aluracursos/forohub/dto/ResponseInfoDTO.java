package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.entity.Response;

import java.time.LocalDateTime;

public record ResponseInfoDTO(

        Long id,
        String message,
        Long idAuthor,
        Long idTopic,
        LocalDateTime creationDate,
        Boolean solution

) {}
