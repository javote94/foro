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
    @Operation(summary = "Crea un tópico")
    public ResponseEntity<TopicInfoDTO> createTopic(@RequestBody @Valid SaveTopicDTO saveTopicDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        TopicInfoDTO topicInfoDTO = topicService.createTopic(saveTopicDTO);
        URI url = uriComponentsBuilder.path("/topic/{id}").buildAndExpand(topicInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(topicInfoDTO);  //HTTP 201 Created
    }

    // GET http://localhost:8080/topics?courseId=4
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Proporciona el listado de tópicos")
    public ResponseEntity<Page<TopicInfoDTO>> listTopics(@RequestParam(required = false) Long courseId,
                                                         @PageableDefault(size = 5) Pageable pageable) {
        Page<TopicInfoDTO> topicPage = topicService.listTopics(courseId, pageable);
        return ResponseEntity.ok(topicPage);
    }

    // GET http://localhost:8080/topics/{topicId}
    @GetMapping("/{topicId}")
    @Operation(summary = "Selecciona un tópico en particular por su ID")
    public ResponseEntity<TopicInfoDTO> getTopic(@PathVariable Long topicId) {
        TopicInfoDTO topicInfoDTO = topicService.getTopicById(topicId);
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
    @DeleteMapping("/{topicId}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Operation(summary = "Realiza una baja lógica de un tópico")
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
