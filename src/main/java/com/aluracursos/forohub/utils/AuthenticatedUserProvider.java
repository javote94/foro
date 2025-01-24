package com.aluracursos.forohub.utils;

import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticatedUserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new IllegalStateException("No authenticated user found.");
        }
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Authenticated user not found in database.");
        }
        return user;
    }
}
