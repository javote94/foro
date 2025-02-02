package com.aluracursos.forohub.util;

import com.aluracursos.forohub.dto.UserInfoDTO;
import com.aluracursos.forohub.entity.User;

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
