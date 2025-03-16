package com.javote.foro.service.impl;

import com.javote.foro.dto.SaveUserDTO;
import com.javote.foro.dto.UserInfoDTO;
import com.javote.foro.enums.Profile;
import com.javote.foro.entity.User;
import com.javote.foro.repository.UserRepository;
import com.javote.foro.service.IUserService;
import com.javote.foro.util.UserMapper;
import com.javote.foro.validation.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<UserValidator> validators; // Inyecta todas las clases que implementan la interfaz UserValidator

    @Override
    @Transactional
    public UserInfoDTO save(SaveUserDTO saveUserDTO) {

        // Ejecuta todas las validaciones
        validators.forEach(validator -> validator.validate(saveUserDTO));

        User user = User.builder()
                .name(saveUserDTO.name())
                .lastName(saveUserDTO.lastName())
                .document(saveUserDTO.document())
                .email(saveUserDTO.email())
                .password(passwordEncoder.encode(saveUserDTO.password()))
                .profile(Profile.USER)
                .active(true)
                .build();

        return UserMapper.toDto(userRepository.save(user));
    }
}
