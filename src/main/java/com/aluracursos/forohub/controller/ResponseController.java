package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.dtos.ResponseInfoDTO;
import com.aluracursos.forohub.dtos.SaveResponseDTO;
import com.aluracursos.forohub.service.IResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/response")
public class ResponseController {

    @Autowired
    private IResponseService service;

    // http://localhost:8080/response/create
    @PostMapping("/create")
    public ResponseEntity<ResponseInfoDTO> createResponse(@RequestBody @Valid SaveResponseDTO saveResponseDTO,
                                                          UriComponentsBuilder uriComponentsBuilder) {

        ResponseInfoDTO responseInfoDTO = service.createResponse(saveResponseDTO);
        URI url = uriComponentsBuilder.path("/response/{id}").buildAndExpand(responseInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(responseInfoDTO);  //HTTP 201 Created
    }

}
