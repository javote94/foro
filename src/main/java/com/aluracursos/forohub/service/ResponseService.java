package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.ResponseInfoDTO;
import com.aluracursos.forohub.dtos.SaveResponseDTO;
import com.aluracursos.forohub.model.Response;
import com.aluracursos.forohub.model.Topic;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.ResponseRepository;
import com.aluracursos.forohub.repository.TopicRepository;
import com.aluracursos.forohub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseService implements IResponseService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ResponseRepository responseRepository;

    @Override
    @Transactional
    public ResponseInfoDTO createResponse(SaveResponseDTO saveResponseDTO) {
        User author = userRepository.getReferenceById(Long.valueOf(saveResponseDTO.idAuthor()));
        Topic topic = topicRepository.getReferenceById(Long.valueOf(saveResponseDTO.idTopic()));
        Response response = responseRepository.save(
                new Response(saveResponseDTO.message(), author, topic)
        );
        return new ResponseInfoDTO(response);
    }
}
