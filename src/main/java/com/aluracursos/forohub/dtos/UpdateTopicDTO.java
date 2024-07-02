package com.aluracursos.forohub.dtos;

import com.aluracursos.forohub.enums.Status;

public record UpdateTopicDTO(

        String title,

        String message,

        Status status
) {}
