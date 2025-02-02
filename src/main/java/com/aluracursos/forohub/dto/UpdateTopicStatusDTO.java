package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.enums.Status;
import jakarta.validation.constraints.NotBlank;

public record UpdateTopicStatusDTO(

        @NotBlank
        Status status
) {}
