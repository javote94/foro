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
@Tag(name = "Topics", description = "Endpoints for creating, updating, viewing, responding to, and deleting discussion topics.")
@RequiredArgsConstructor
public class TopicController {

    private final ITopicService topicService;
    private final IResponseService responseService;

    // POST http://localhost:8080/topics
    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Create a new topic", description = "Users can create topics only in courses they are enrolled in. Moderators can only create topics in courses they moderate. Admins have no restrictions.")
    public ResponseEntity<TopicInfoDTO> createTopic(@RequestBody @Valid SaveTopicDTO saveTopicDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        TopicInfoDTO topicInfoDTO = topicService.createTopic(saveTopicDTO);
        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topicInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(topicInfoDTO);  //HTTP 201 Created
    }

    // GET http://localhost:8080/topics?courseId=X
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "List topics", description = "Users see topics in courses they are enrolled in. Moderators see topics in courses they moderate. Admins see all topics. Filtering by course is optional.")
    public ResponseEntity<Page<TopicInfoDTO>> listTopics(@RequestParam(required = false) Long courseId,
                                                         @PageableDefault(size = 5) Pageable pageable) {
        Page<TopicInfoDTO> topicPage = topicService.listTopics(courseId, pageable);
        return ResponseEntity.ok(topicPage);
    }

    // GET http://localhost:8080/topics/{topicId}
    @GetMapping("/{topicId}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Get topic by ID", description = "Returns a topic along with its responses. Same access rules apply as in the topic listing.")
    public ResponseEntity<TopicInfoDTO> getTopic(@PathVariable Long topicId) {
        TopicInfoDTO topicInfoDTO = topicService.getTopicById(topicId);
        return ResponseEntity.ok(topicInfoDTO);
    }

    // PATCH http://localhost:8080/topics/{topicId}
    @PatchMapping("/{topicId}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Update topic content", description = "Only the topic author can update the title or message.")
    public ResponseEntity<TopicInfoDTO> updateTopic(@PathVariable Long topicId, @RequestBody UpdateTopicDTO updateTopicDTO) {
        TopicInfoDTO topicInfoDTO = topicService.updateTopic(topicId, updateTopicDTO);
        return ResponseEntity.ok(topicInfoDTO);
    }

    // DELETE http://localhost:8080/topics/{topicId}
    @DeleteMapping("/{topicId}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Logically delete a topic", description = "Users can delete their own topics. Moderators can delete topics from their courses. Admins can delete any topic. All associated responses are also deactivated.")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
        return ResponseEntity.noContent().build();
    }

    // POST http://localhost:8080/topics/{topicId}/responses
    @PostMapping("/{topicId}/responses")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Respond to a topic", description = "Users can respond to topics in courses they are enrolled in. Moderators can respond to topics in their courses. Admins can respond to any topic.")
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
