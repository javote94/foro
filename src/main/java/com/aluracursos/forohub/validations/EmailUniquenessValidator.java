package com.aluracursos.forohub.validations;

import com.aluracursos.forohub.dtos.SaveUserDTO;
import com.aluracursos.forohub.exceptions.EmailAlreadyExistsException;
import com.aluracursos.forohub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailUniquenessValidator implements UserValidator{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(SaveUserDTO saveUserDTO) {
        if (userRepository.existsByEmail(saveUserDTO.email())) {
            throw new EmailAlreadyExistsException("The email " + saveUserDTO.email() + " is already registered.");
        }
    }
}
