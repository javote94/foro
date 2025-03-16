package com.javote.foro.dto;

import com.javote.foro.enums.Status;
import jakarta.validation.constraints.NotBlank;

public record UpdateTopicStatusDTO(

        @NotBlank
        Status status
) {}
