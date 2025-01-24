package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITopicService {
    TopicInfoDTO createTopic(SaveTopicDTO saveTopicDTO);
    Page<TopicInfoDTO> listTopics(String courseName, Integer year, Pageable pageable);
    TopicInfoDTO getTopicById(Long topicId);
    TopicInfoDTO changeTopicStatus(Long topicId, UpdateTopicStatusDTO updateTopicStatusDTO);
    TopicInfoDTO changeTopicActiveStatus(Long topicId, UpdateTopicActiveDTO updateTopicActiveDTO);
    TopicInfoDTO updateTopicContent(Long topicId, UpdateTopicContentDTO updateTopicContentDTO);
    void deleteTopic(Long topicId);
}
