package com.javote.foro.service;

import com.javote.foro.dto.SaveUserDTO;
import com.javote.foro.dto.UserInfoDTO;

public interface IUserService {
    UserInfoDTO save(SaveUserDTO saveUserDTO);
}
