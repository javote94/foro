package com.aluracursos.forohub.dtos;

public record UserInfoDTO(
        Long id,
        String name,
        String lastName,
        String document,
        String email
) {}
