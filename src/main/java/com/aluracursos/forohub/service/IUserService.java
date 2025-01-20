package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.SaveUserDTO;
import com.aluracursos.forohub.dtos.UserInfoDTO;
import com.aluracursos.forohub.model.User;

public interface IUserService {
    UserInfoDTO save(SaveUserDTO saveUserDTO);
    User getAuthenticatedUser();
}
