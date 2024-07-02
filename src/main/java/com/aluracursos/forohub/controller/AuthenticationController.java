package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.dtos.AuthUserDTO;
import com.aluracursos.forohub.dtos.JwtTokenDTO;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<JwtTokenDTO> authenticateUser(@RequestBody @Valid AuthUserDTO authUserDTO) {

        // Se crea un “token de autenticación” que actúa como contenedor de las credenciales del
        // cliente para que pueda ser procesado por AuthenticationManager
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                authUserDTO.email(),
                authUserDTO.password()
        );

        // Si las credenciales son válidas, el AuthenticationManager devuelve un objeto Authentication
        // que representa al usuario autenticado
        Authentication authenticatedUser = authenticationManager.authenticate(authToken);

        // Genera un JWT con la información del usuario autenticado
        String jwtToken = jwtService.createJwtToken((User) authenticatedUser.getPrincipal());

        // Retorna espuesta HTTP 200 (OK) que contiene el token JWT en el cuerpo de la respuesta.
        return ResponseEntity.ok(new JwtTokenDTO(jwtToken));

    }


}
