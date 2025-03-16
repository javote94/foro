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

    // PATCH http://localhost:8080/responses/{responseId}/solution
    @PatchMapping("/{responseId}/solution")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Operation(summary = "Marca una respuesta como solución y cambia el estado del tópico a RESOLVED")
    public ResponseEntity<ResponseInfoDTO> markResponseAsSolution(@PathVariable Long responseId) {
        ResponseInfoDTO responseInfoDTO = responseService.markResponseAsSolution(responseId);
        return ResponseEntity.ok(responseInfoDTO);
    }

}
