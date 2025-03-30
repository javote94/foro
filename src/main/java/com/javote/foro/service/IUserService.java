package com.javote.foro.service;

import com.javote.foro.dto.SaveModeratorDTO;
import com.javote.foro.dto.SaveUserDTO;
import com.javote.foro.dto.UserInfoDTO;

public interface IUserService {
    UserInfoDTO saveStudent(SaveUserDTO saveUserDTO);
    UserInfoDTO saveModerator(SaveModeratorDTO saveModeratorDTO);
}
