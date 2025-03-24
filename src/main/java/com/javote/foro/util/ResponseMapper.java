package com.javote.foro.util;

import com.javote.foro.dto.ResponseInfoDTO;
import com.javote.foro.entity.Response;

public class ResponseMapper {

    public static ResponseInfoDTO toDto(Response response){
        return new ResponseInfoDTO(
                response.getId(),
                response.getMessage(),
                response.getAuthor().getId(),
                response.getAuthor().getName() + " " + response.getAuthor().getLastName(),
                response.getTopic().getId(),
                response.getCreationDate(),
                response.getSolution()
        );
    }

}
