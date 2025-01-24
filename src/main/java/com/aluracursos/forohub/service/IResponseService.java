package com.aluracursos.forohub.service;


import com.aluracursos.forohub.dtos.ResponseInfoDTO;
import com.aluracursos.forohub.dtos.SaveResponseDTO;

public interface IResponseService {

    ResponseInfoDTO createResponse(Long topicId, SaveResponseDTO saveResponseDTO);

}
