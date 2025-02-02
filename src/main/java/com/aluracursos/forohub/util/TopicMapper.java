package com.aluracursos.forohub.util;

import com.aluracursos.forohub.dto.TopicInfoDTO;
import com.aluracursos.forohub.entity.Topic;

public class TopicMapper {

    public static TopicInfoDTO toDto(Topic topic) {
        return new TopicInfoDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getAuthor().getId(),
                topic.getCourse().getId(),
                topic.getCreationDate(),
                topic.getStatus().toString()
        );
    }


}
