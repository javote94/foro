package com.javote.foro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Detailed information of a course and its enrolled students.")
public record CourseInfoDTO(

        @Schema(description = "Course ID", example = "3")
        Long id,

        @Schema(description = "Course name", example = "Advanced Java")
        String name,

        @Schema(description = "Moderator ID", example = "7")
        Long moderatorId,

        @Schema(description = "List of students enrolled in the course")
        List<UserInfoDTO> students
) {}
