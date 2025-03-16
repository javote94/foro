package com.javote.foro.service;


import com.javote.foro.dto.ResponseInfoDTO;
import com.javote.foro.dto.SaveResponseDTO;

public interface IResponseService {

    ResponseInfoDTO createResponse(Long topicId, SaveResponseDTO saveResponseDTO);
    ResponseInfoDTO markResponseAsSolution(Long responseId);
}
