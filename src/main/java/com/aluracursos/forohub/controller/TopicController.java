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
import org.springframework.security.access.prepost.PreAuthorize;
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

    // GET http://localhost:8080/topics/{topicId}
    @GetMapping("/{idTopic}")
    @Operation(summary = "Selecciona un tópico en particular por su ID")
    public ResponseEntity<TopicInfoDTO> getTopic(@PathVariable Long topicId) {
        TopicInfoDTO topicInfoDTO = topicService.getTopicById(topicId);
        return ResponseEntity.ok(topicInfoDTO);
    }

    // PATCH http://localhost:8080/topics/{topicId}/status
    @PatchMapping("/{topicId}/status")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Operation(summary = "Cambia el estado del tópico")
    public ResponseEntity<TopicInfoDTO> changeTopicStatus(@PathVariable Long topicId, @RequestBody UpdateTopicStatusDTO updateTopicStatusDTO) {
        TopicInfoDTO topicInfoDTO = topicService.changeTopicStatus(topicId, updateTopicStatusDTO);
        return ResponseEntity.ok(topicInfoDTO);
    }

    // PATCH http://localhost:8080/topics/{topicId}/active
    @PatchMapping("/{topicId}/active")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Operation(summary = "Cambia si el tópico está activo o inactivo")
    public ResponseEntity<TopicInfoDTO> changeTopicActiveStatus(@PathVariable Long topicId, @RequestBody UpdateTopicActiveDTO updateTopicActiveDTO) {
        TopicInfoDTO topicInfoDTO = topicService.changeTopicActiveStatus(topicId, updateTopicActiveDTO);
        return ResponseEntity.ok(topicInfoDTO);
    }

    // PATCH http://localhost:8080/topics/{topicId}/content
    @PatchMapping("/{topicId}/content")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Modifica el título o mensaje del tópico")
    public ResponseEntity<TopicInfoDTO> updateTopicContent(@PathVariable Long topicId, @RequestBody UpdateTopicContentDTO updateTopicContentDTO) {
        TopicInfoDTO topicInfoDTO = topicService.updateTopicContent(topicId, updateTopicContentDTO);
        return ResponseEntity.ok(topicInfoDTO);
    }

    // DELETE http://localhost:8080/topics/{topicId}
    @DeleteMapping("/{idTopic}")
    @Operation(summary = "Realiza una baja lógica de un tópico alojado en la base de datos")
    public ResponseEntity deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
        return ResponseEntity.noContent().build();
    }

    // POST http://localhost:8080/topics/{topicId}/responses
    @PostMapping("/{topicId}/responses")
    public ResponseEntity<ResponseInfoDTO> createResponse(@PathVariable Long topicId,
                                                          @RequestBody @Valid SaveResponseDTO saveResponseDTO,
                                                          UriComponentsBuilder uriComponentsBuilder) {

        ResponseInfoDTO responseInfoDTO = responseService.createResponse(topicId, saveResponseDTO);
        URI url = uriComponentsBuilder.path("/topics/{idTopic}/responses/{id}")
                .buildAndExpand(topicId, responseInfoDTO.id())
                .toUri();
        return ResponseEntity.created(url).body(responseInfoDTO);  //HTTP 201 Created
    }
}
