package com.aluracursos.forohub.service.impl;

import com.aluracursos.forohub.dto.ResponseInfoDTO;
import com.aluracursos.forohub.dto.SaveResponseDTO;
import com.aluracursos.forohub.exception.TopicNotFoundException;
import com.aluracursos.forohub.entity.Response;
import com.aluracursos.forohub.entity.Topic;
import com.aluracursos.forohub.entity.User;
import com.aluracursos.forohub.repository.ResponseRepository;
import com.aluracursos.forohub.repository.TopicRepository;
import com.aluracursos.forohub.service.IResponseService;
import com.aluracursos.forohub.util.AuthenticatedUserProvider;
import com.aluracursos.forohub.util.ResponseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResponseService implements IResponseService {

    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    @Transactional
    public ResponseInfoDTO createResponse(Long topicId, SaveResponseDTO saveResponseDTO) {

        User author = authenticatedUserProvider.getAuthenticatedUser();

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found with id: " + topicId));

        Response response = Response.builder()
                .message(saveResponseDTO.message())
                .topic(topic)
                .author(author)
                .creationDate(LocalDateTime.now())
                .active(true)
                .solution(false)
                .build();

        return ResponseMapper.toDto(responseRepository.save(response));
    }
}
