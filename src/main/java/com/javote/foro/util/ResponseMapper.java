package com.javote.foro.util;

import com.javote.foro.dto.ResponseInfoDTO;
import com.javote.foro.entity.Response;

public class ResponseMapper {

    public static ResponseInfoDTO toDto(Response response){
        return new ResponseInfoDTO(
                response.getId(),
                response.getMessage(),
                response.getAuthor().getId(),
                response.getTopic().getId(),
                response.getTopic().getStatus().toString(),
                response.getCreationDate(),
                response.getSolution()
        );
    }

}
