package com.javote.foro.dto;

import java.util.List;

public record CourseInfoDTO(
        Long id,
        String name,
        Long moderatorId,
        List<UserInfoDTO> students
) {}
