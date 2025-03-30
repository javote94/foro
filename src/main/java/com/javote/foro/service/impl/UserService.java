package com.javote.foro.service.impl;

import com.javote.foro.dto.SaveModeratorDTO;
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
    public UserInfoDTO saveStudent(SaveUserDTO saveUserDTO) {

        // Ejecuta todas las validaciones
        validators.forEach(v -> v.validate(saveUserDTO));

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

    @Override
    @Transactional
    public UserInfoDTO saveModerator(SaveModeratorDTO saveModeratorDTO) {

        SaveUserDTO asUser = new SaveUserDTO(
                saveModeratorDTO.name(),
                saveModeratorDTO.lastName(),
                saveModeratorDTO.document(),
                saveModeratorDTO.email(),
                saveModeratorDTO.password()
        );

        validators.forEach(v -> v.validate(asUser));

        User user = User.builder()
                .name(saveModeratorDTO.name())
                .lastName(saveModeratorDTO.lastName())
                .document(saveModeratorDTO.document())
                .email(saveModeratorDTO.email())
                .password(passwordEncoder.encode(saveModeratorDTO.password()))
                .profile(Profile.MODERATOR)
                .active(true)
                .build();

        return UserMapper.toDto(userRepository.save(user));
    }
}
