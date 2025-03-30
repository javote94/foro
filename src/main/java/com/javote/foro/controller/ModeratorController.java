package com.javote.foro.controller;

import com.javote.foro.dto.SaveModeratorDTO;
import com.javote.foro.dto.UserInfoDTO;
import com.javote.foro.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/moderators")
@Tag(name = "Registro de moderadores", description = "Solo los administradores pueden registrar moderadores")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class ModeratorController {

    private final IUserService userService;

    // POST http://localhost:8080/moderators
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar un nuevo moderador")
    public ResponseEntity<UserInfoDTO> registerModerator(@RequestBody @Valid SaveModeratorDTO saveModeratorDTO,
                                                         UriComponentsBuilder uriBuilder) {
        UserInfoDTO userInfoDTO = userService.saveModerator(saveModeratorDTO);
        URI url = uriBuilder.path("/user/{id}").buildAndExpand(userInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(userInfoDTO);
    }


}
