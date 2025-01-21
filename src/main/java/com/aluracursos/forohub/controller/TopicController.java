package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.dtos.*;
import com.aluracursos.forohub.service.IResponseService;
import com.aluracursos.forohub.service.ITopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos", description = "Endpoints para realizar operaciones CRUD sobre los tópicos del foro")
public class TopicController {

    private final ITopicService topicService;
    private final IResponseService responseService;

    @Autowired
    public TopicController(ITopicService topicService, IResponseService responseService) {
        this.topicService = topicService;
        this.responseService = responseService;
    }

    // POST http://localhost:8080/topics
    @PostMapping
    @Operation(summary = "Registra un nuevo tópico en la base de datos")
    public ResponseEntity<TopicInfoDTO> createTopic(@RequestBody @Valid SaveTopicDTO saveTopicDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        TopicInfoDTO topicInfoDTO = topicService.createTopic(saveTopicDTO);
        URI url = uriComponentsBuilder.path("/topic/{id}").buildAndExpand(topicInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(topicInfoDTO);  //HTTP 201 Created
    }

    // GET http://localhost:8080/topics?courseName=Curso%20Java&year=2023
    @GetMapping
    @Operation(summary = "Proporciona el listado de tópico almacenado en la base de datos")
    public ResponseEntity<Page<TopicInfoDTO>> listTopics(@RequestParam(required = false) String courseName,
                                                         @RequestParam(required = false) Integer year,
                                                         @PageableDefault(size = 5) Pageable pageable) {
        Page<TopicInfoDTO> topicPage = topicService.listTopics(courseName, year, pageable);
        return ResponseEntity.ok(topicPage);
    }

    // GET http://localhost:8080/topics/{idTopic}
    @GetMapping("/{idTopic}")
    @Operation(summary = "Selecciona un tópico en particular por su ID")
    public ResponseEntity<TopicInfoDTO> getTopic(@PathVariable Long idTopic) {
        TopicInfoDTO topicInfoDTO = topicService.getTopicById(idTopic);
        return ResponseEntity.ok(topicInfoDTO);
    }

    // PUT http://localhost:8080/topics/{idTopic}
    @PutMapping("/{idTopic}")
    @Operation(summary = "Actualiza los datos de un tópico existente en la base de datos")
    public ResponseEntity<TopicInfoDTO> updateTopic(@PathVariable Long idTopic, @RequestBody UpdateTopicDTO updateTopicDTO) {
        TopicInfoDTO topicInfoDTO = topicService.updateTopic(idTopic, updateTopicDTO);
        return ResponseEntity.ok(topicInfoDTO);
    }

     // DELETE http://localhost:8080/topics/{idTopic}
    @DeleteMapping("/{idTopic}")
    @Operation(summary = "Realiza una baja lógica de un tópico alojado en la base de datos")
    public ResponseEntity deleteTopic(@PathVariable Long idTopic) {
        topicService.deleteTopic(idTopic);
        return ResponseEntity.noContent().build();
    }

    // POST http://localhost:8080/topics/{idTopic}/responses
    @PostMapping("/{idTopic}/responses")
    public ResponseEntity<ResponseInfoDTO> createResponse(@PathVariable Long idTopic,
                                                          @RequestBody @Valid SaveResponseDTO saveResponseDTO,
                                                          UriComponentsBuilder uriComponentsBuilder) {

        ResponseInfoDTO responseInfoDTO = responseService.createResponse(idTopic, saveResponseDTO);
        URI url = uriComponentsBuilder.path("/topics/{idTopic}/responses/{id}")
                .buildAndExpand(idTopic, responseInfoDTO.id())
                .toUri();
        return ResponseEntity.created(url).body(responseInfoDTO);  //HTTP 201 Created
    }
}
