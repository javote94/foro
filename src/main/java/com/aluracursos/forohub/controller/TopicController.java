package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.dtos.SaveTopicDTO;
import com.aluracursos.forohub.dtos.TopicInfoDTO;
import com.aluracursos.forohub.dtos.UpdateTopicDTO;
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
@RequestMapping("/topic")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos", description = "Endpoints para realizar operaciones CRUD sobre los tópicos del foro")
public class TopicController {

    @Autowired
    private ITopicService service;

    @PostMapping("/create")
    @Operation(summary = "Registra un nuevo tópico en la base de datos")
    public ResponseEntity<TopicInfoDTO> createTopic(@RequestBody @Valid SaveTopicDTO saveTopicDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        TopicInfoDTO topicInfoDTO = service.createTopic(saveTopicDTO);
        URI url = uriComponentsBuilder.path("/topic/{id}").buildAndExpand(topicInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(topicInfoDTO);  //HTTP 201 Created
    }

    // Ej: localhost:8080/topic/list?courseName=Curso%20Java&year=2023
    @GetMapping("/list")
    @Operation(summary = "Proporciona el listado de tópico almacenado en la base de datos")
    public ResponseEntity<Page<TopicInfoDTO>> listTopics(@RequestParam(required = false) String courseName,
                                                         @RequestParam(required = false) Integer year,
                                                         @PageableDefault(size = 5) Pageable pageable) {
        Page<TopicInfoDTO> topicPage = service.listTopics(courseName, year, pageable);
        return ResponseEntity.ok(topicPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Selecciona un tópico en particular por su ID")
    public ResponseEntity<TopicInfoDTO> getTopic(@PathVariable Long id) {
        TopicInfoDTO topicInfoDTO = service.getTopicById(id);
        return ResponseEntity.ok(topicInfoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza los datos de un tópico existente en la base de datos")
    public ResponseEntity<TopicInfoDTO> updateTopic(@PathVariable Long id, @RequestBody UpdateTopicDTO updateTopicDTO) {
        TopicInfoDTO topicInfoDTO = service.updateTopic(id, updateTopicDTO);
        return ResponseEntity.ok(topicInfoDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Realiza una baja lógica de un tópico alojado en la base de datos")
    public ResponseEntity deleteTopic(@PathVariable Long id) {
        service.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
