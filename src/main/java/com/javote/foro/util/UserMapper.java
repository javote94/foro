package com.javote.foro.util;

import com.javote.foro.dto.UserInfoDTO;
import com.javote.foro.entity.User;

public class UserMapper {

    public static UserInfoDTO toDto(User user){
        return new UserInfoDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getDocument(),
                user.getEmail()
        );
    }

}
