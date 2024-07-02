package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.SaveTopicDTO;
import com.aluracursos.forohub.dtos.TopicInfoDTO;
import com.aluracursos.forohub.dtos.UpdateTopicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITopicService {
    TopicInfoDTO createTopic(SaveTopicDTO saveTopicDTO);
    Page<TopicInfoDTO> listTopics(String courseName, Integer year, Pageable pageable);
    TopicInfoDTO getTopicById(Long id);
    TopicInfoDTO updateTopic(Long id, UpdateTopicDTO updateTopicDTO);
    void deleteTopic(Long id);
}
