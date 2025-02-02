package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dto.SaveUserDTO;
import com.aluracursos.forohub.dto.UserInfoDTO;

public interface IUserService {
    UserInfoDTO save(SaveUserDTO saveUserDTO);
}
