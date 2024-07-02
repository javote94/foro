package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.SaveUserDTO;
import com.aluracursos.forohub.dtos.UserInfoDTO;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserInfoDTO save(SaveUserDTO saveUserDTO) {

        String encodedPassword = passwordEncoder.encode(saveUserDTO.password());

        User user = new User(saveUserDTO);
        user.setPassword(encodedPassword);

        user = repository.save(user);
        return new UserInfoDTO(
                user.getId(),
                user.getName(),
                user.getDocument(),
                user.getEmail()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return user;
    }
}
