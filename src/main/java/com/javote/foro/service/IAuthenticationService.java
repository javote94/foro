package com.javote.foro.service;

import com.javote.foro.dto.AuthUserDTO;
import com.javote.foro.dto.JwtTokenDTO;

public interface IAuthenticationService {

    JwtTokenDTO login(AuthUserDTO authUserDTO);

}
