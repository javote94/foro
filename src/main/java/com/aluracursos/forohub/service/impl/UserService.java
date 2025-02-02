package com.aluracursos.forohub.service.impl;

import com.aluracursos.forohub.dto.SaveUserDTO;
import com.aluracursos.forohub.dto.UserInfoDTO;
import com.aluracursos.forohub.enums.Profile;
import com.aluracursos.forohub.entity.User;
import com.aluracursos.forohub.repository.UserRepository;
import com.aluracursos.forohub.service.IUserService;
import com.aluracursos.forohub.util.UserMapper;
import com.aluracursos.forohub.validation.UserValidator;
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
