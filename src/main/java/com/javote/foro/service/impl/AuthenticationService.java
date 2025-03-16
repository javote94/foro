package com.javote.foro.service.impl;

import com.javote.foro.dto.AuthUserDTO;
import com.javote.foro.dto.JwtTokenDTO;
import com.javote.foro.entity.User;
import com.javote.foro.security.JwtService;
import com.javote.foro.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public JwtTokenDTO login(AuthUserDTO authUserDTO) {

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

        return new JwtTokenDTO(jwtToken);
    }

}
