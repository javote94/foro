package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.SaveUserDTO;
import com.aluracursos.forohub.dtos.UserInfoDTO;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.UserRepository;
import com.aluracursos.forohub.validations.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<UserValidator> validators; // Inyecta todas las clases que implementan la interfaz UserValidator

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, List<UserValidator> validators) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validators = validators;
    }

    @Override
    @Transactional
    public UserInfoDTO save(SaveUserDTO saveUserDTO) {

        // Ejecuta todas las validaciones
        validators.forEach(validator -> validator.validate(saveUserDTO));

        String encodedPassword = passwordEncoder.encode(saveUserDTO.password());

        User user = new User(saveUserDTO);
        user.setPassword(encodedPassword);

        user = userRepository.save(user);

        return new UserInfoDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getDocument(),
                user.getEmail()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return user;
    }




}
