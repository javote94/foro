package com.aluracursos.forohub.util;

import com.aluracursos.forohub.dto.ResponseInfoDTO;
import com.aluracursos.forohub.entity.Response;

public class ResponseMapper {

    public static ResponseInfoDTO toDto(Response response){
        return new ResponseInfoDTO(
                response.getId(),
                response.getMessage(),
                response.getAuthor().getId(),
                response.getTopic().getId(),
                response.getCreationDate(),
                response.getSolution()
        );
    }

}
