package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.Size;

public record UpdateTopicContentDTO(

        @Size(min = 10, message = "Title must be at least 10 characters long")
        String title,

        @Size(min = 10, message = "Message must be at least 10 characters long")
        String message
) {}
