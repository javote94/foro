package com.javote.foro.dto;

public record UserInfoDTO(
        Long id,
        String name,
        String lastName,
        String document,
        String email
) {}
