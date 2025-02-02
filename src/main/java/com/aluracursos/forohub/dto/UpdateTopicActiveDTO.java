package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTopicActiveDTO(
        @NotBlank
        Boolean active
) {}
