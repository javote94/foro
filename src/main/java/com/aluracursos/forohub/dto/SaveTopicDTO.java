package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SaveTopicDTO(

    @NotBlank(message = "Course ID is mandatory")
    String courseId,

    @NotBlank(message = "Title is mandatory")
    @Size(min = 10, message = "Title must be at least 10 characters long")
    String title,

    @NotBlank(message = "Message is mandatory")
    @Size(min = 10, message = "Message must be at least 10 characters long")
    String message

) {}
