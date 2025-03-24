package com.javote.foro.service;

import com.javote.foro.dto.SaveTopicDTO;
import com.javote.foro.dto.TopicInfoDTO;
import com.javote.foro.dto.UpdateTopicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITopicService {
    TopicInfoDTO createTopic(SaveTopicDTO saveTopicDTO);
    Page<TopicInfoDTO> listTopics(Long courseId, Pageable pageable);
    TopicInfoDTO getTopicById(Long topicId);
    TopicInfoDTO updateTopic(Long topicId, UpdateTopicDTO updateTopicDTO);
    void deleteTopic(Long topicId);
}
