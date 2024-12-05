package com.aluracursos.forohub.dtos;

import com.aluracursos.forohub.model.Response;

import java.time.LocalDateTime;

public record ResponseInfoDTO(

        Long id,
        String message,
        Long idAuthor,
        Long idTopic,
        LocalDateTime creationDate,
        Boolean solution

) {
    public ResponseInfoDTO(Response response) {
        this(
            response.getId(),
            response.getMessage(),
            response.getAuthor().getId(),
            response.getTopic().getId(),
            response.getCreationDate(),
            response.getSolution()
        );

    }
}
