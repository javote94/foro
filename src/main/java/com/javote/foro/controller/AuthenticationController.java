package com.javote.foro.controller;

import com.javote.foro.dto.AuthUserDTO;
import com.javote.foro.dto.JwtTokenDTO;
import com.javote.foro.service.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Authentication", description = "Provides JWT tokens for authenticated access to protected API endpoints.")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // POST http://localhost:8080/login
    @PostMapping
    @Operation(summary = "Authenticate user", description = "Validates user credentials and returns a JWT token if successful.")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody @Valid AuthUserDTO authUserDTO) {
        // Retorna respuesta HTTP 200 (OK) que contiene el token JWT en el cuerpo de la respuesta.
        return ResponseEntity.ok(authenticationService.login(authUserDTO));
    }
}
