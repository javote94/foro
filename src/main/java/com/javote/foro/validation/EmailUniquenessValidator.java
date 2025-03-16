package com.javote.foro.validation;

import com.javote.foro.dto.SaveUserDTO;
import com.javote.foro.exception.EmailAlreadyExistsException;
import com.javote.foro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUniquenessValidator implements UserValidator{

    private final UserRepository userRepository;

    @Override
    public void validate(SaveUserDTO saveUserDTO) {
        if (userRepository.existsByEmail(saveUserDTO.email())) {
            throw new EmailAlreadyExistsException("The email " + saveUserDTO.email() + " is already registered.");
        }
    }
}
