package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.dto.AuthUserDTO;
import com.aluracursos.forohub.dto.JwtTokenDTO;
import com.aluracursos.forohub.service.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Este controlador se encarga de autenticar al usuario y generar un token JWT
// que se devuelve como respuesta para que el cliente pueda autenticarse cuando
// realice solicitudes subsecuentes a otros endpoints protegidos en la API.

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación", description = "Solicita el token JWT para que el usuario pueda autenticarse cuando realice solicitudes a otros endpoints protegidos de la API")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // http://localhost:8080/login
    @PostMapping
    public ResponseEntity<JwtTokenDTO> login(@RequestBody @Valid AuthUserDTO authUserDTO) {
        // Retorna respuesta HTTP 200 (OK) que contiene el token JWT en el cuerpo de la respuesta.
        return ResponseEntity.ok(authenticationService.login(authUserDTO));
    }
}
