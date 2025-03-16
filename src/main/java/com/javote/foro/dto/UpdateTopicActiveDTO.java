package com.javote.foro.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTopicActiveDTO(
        @NotBlank
        Boolean active
) {}
