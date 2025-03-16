package com.javote.foro.util;

import com.javote.foro.dto.TopicInfoDTO;
import com.javote.foro.entity.Topic;

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
