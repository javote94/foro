package com.aluracursos.forohub.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateTopicActiveDTO(
        @NotBlank
        Boolean active
) {}
