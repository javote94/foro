package com.aluracursos.forohub.dtos;

import com.aluracursos.forohub.enums.Status;
import jakarta.validation.constraints.NotBlank;

public record UpdateTopicStatusDTO(

        @NotBlank
        Status status
) {}
