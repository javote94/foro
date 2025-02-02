package com.aluracursos.forohub.service;


import com.aluracursos.forohub.dto.ResponseInfoDTO;
import com.aluracursos.forohub.dto.SaveResponseDTO;

public interface IResponseService {

    ResponseInfoDTO createResponse(Long topicId, SaveResponseDTO saveResponseDTO);

}
