package com.javote.foro.controller;

import com.javote.foro.dto.*;
import com.javote.foro.service.IResponseService;
import com.javote.foro.service.ITopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TopicController {

    private final ITopicService topicService;
    private final IResponseService responseService;

    // POST http://localhost:8080/topics
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Crear un tópico")
    public ResponseEntity<TopicInfoDTO> createTopic(@RequestBody @Valid SaveTopicDTO saveTopicDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        TopicInfoDTO topicInfoDTO = topicService.createTopic(saveTopicDTO);
        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topicInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(topicInfoDTO);  //HTTP 201 Created
    }

    // GET http://localhost:8080/topics?courseId=X
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Listar los tópicos")
    public ResponseEntity<Page<TopicInfoDTO>> listTopics(@RequestParam(required = false) Long courseId,
                                                         @PageableDefault(size = 5) Pageable pageable) {
        Page<TopicInfoDTO> topicPage = topicService.listTopics(courseId, pageable);
        return ResponseEntity.ok(topicPage);
    }

    // GET http://localhost:8080/topics/{topicId}
    @GetMapping("/{topicId}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Seleccionar un tópico por su ID")
    public ResponseEntity<TopicInfoDTO> getTopic(@PathVariable Long topicId) {
        TopicInfoDTO topicInfoDTO = topicService.getTopicById(topicId);
        return ResponseEntity.ok(topicInfoDTO);
    }

    // PATCH http://localhost:8080/topics/{topicId}
    @PatchMapping("/{topicId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Modificar el título y/o mensaje de un tópico")
    public ResponseEntity<TopicInfoDTO> updateTopic(@PathVariable Long topicId, @RequestBody UpdateTopicDTO updateTopicDTO) {
        TopicInfoDTO topicInfoDTO = topicService.updateTopic(topicId, updateTopicDTO);
        return ResponseEntity.ok(topicInfoDTO);
    }

    // DELETE http://localhost:8080/topics/{topicId}
    @DeleteMapping("/{topicId}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Realizar baja lógica de un tópico")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
        return ResponseEntity.noContent().build();
    }

    // POST http://localhost:8080/topics/{topicId}/responses
    @PostMapping("/{topicId}/responses")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Responder a un tópico")
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
