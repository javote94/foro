package com.javote.foro.util;

import com.javote.foro.dto.ResponseInfoDTO;
import com.javote.foro.dto.TopicInfoDTO;
import com.javote.foro.entity.Response;
import com.javote.foro.entity.Topic;

import java.util.List;

public class TopicMapper {

    public static TopicInfoDTO toDto(Topic topic) {

        List<ResponseInfoDTO> responseDTOs = topic.getResponses() == null
                ? List.of()
                : topic.getResponses().stream()
                .filter(Response::getActive)
                .map(ResponseMapper::toDto)
                .toList();

        return new TopicInfoDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getAuthor().getId(),
                topic.getAuthor().getName() + " " + topic.getAuthor().getLastName(),
                topic.getCourse().getId(),
                topic.getCreationDate(),
                topic.getStatus().toString(),
                responseDTOs
        );
    }
}
