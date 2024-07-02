package com.aluracursos.forohub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SaveTopicDTO(

    @NotBlank(message = "Author ID is mandatory")
    String idAuthor,

    @NotBlank(message = "Title is mandatory")
    @Size(min = 10, message = "Title must be at least 10 characters long")
    String title,

    @NotBlank(message = "Message is mandatory")
    @Size(min = 10, message = "Message must be at least 10 characters long")
    String message,

    @NotBlank(message = "Course name is mandatory")
    String courseName

) {}
