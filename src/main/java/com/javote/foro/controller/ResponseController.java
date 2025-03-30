package com.javote.foro.controller;

import com.javote.foro.dto.ResponseInfoDTO;
import com.javote.foro.service.IResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responses")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Responses", description = "Endpoints to manage responses to forum topics.")
@RequiredArgsConstructor
public class ResponseController {

    private final IResponseService responseService;

    // PATCH http://localhost:8080/responses/{responseId}/toggle-solution
    @PatchMapping("/{responseId}/toggle-solution")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Mark or unmark a response as the solution", description = "Only the topic author, a course moderator, or an admin can toggle a response's solution status. Only one solution allowed per topic.")
    public ResponseEntity<ResponseInfoDTO> toggleSolution(@PathVariable Long responseId) {
        ResponseInfoDTO responseInfoDTO = responseService.toggleSolutionStatus(responseId);
        return ResponseEntity.ok(responseInfoDTO);
    }

    // DELETE http://localhost:8080/responses/{responseId}
    @DeleteMapping("/{responseId}")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Logically delete a response", description = "Users can delete their own responses. Moderators can delete responses from topics in their courses. Admins can delete any response.")
    public ResponseEntity<Void> deleteResponse(@PathVariable Long responseId) {
        responseService.deleteResponse(responseId);
        return ResponseEntity.noContent().build();
    }

}
