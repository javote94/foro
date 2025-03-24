package com.javote.foro.controller;

import com.javote.foro.dto.ResponseInfoDTO;
import com.javote.foro.service.IResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/responses")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respuestas", description = "Endpoints para realizar operaciones sobre las respuestas de los tópicos")
@RequiredArgsConstructor
public class ResponseController {

    private final IResponseService responseService;

    // PATCH http://localhost:8080/responses/{responseId}/toggle-solution
    @PatchMapping("/{responseId}/toggle-solution")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Marca o desmarcar una respuesta como solución")
    public ResponseEntity<ResponseInfoDTO> toggleSolution(@PathVariable Long responseId) {
        ResponseInfoDTO responseInfoDTO = responseService.toggleSolutionStatus(responseId);
        return ResponseEntity.ok(responseInfoDTO);
    }

}
