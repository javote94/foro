package com.aluracursos.forohub.validation;

import com.aluracursos.forohub.dto.SaveUserDTO;
import com.aluracursos.forohub.exception.EmailAlreadyExistsException;
import com.aluracursos.forohub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
