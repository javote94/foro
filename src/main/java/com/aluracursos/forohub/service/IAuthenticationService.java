package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dto.AuthUserDTO;
import com.aluracursos.forohub.dto.JwtTokenDTO;

public interface IAuthenticationService {

    JwtTokenDTO login(AuthUserDTO authUserDTO);

}
