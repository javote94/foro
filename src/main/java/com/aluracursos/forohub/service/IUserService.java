package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.SaveUserDTO;
import com.aluracursos.forohub.dtos.UserInfoDTO;

public interface IUserService {
    UserInfoDTO save(SaveUserDTO saveUserDTO);
}
