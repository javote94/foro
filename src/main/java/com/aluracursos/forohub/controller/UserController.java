package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.dtos.SaveUserDTO;
import com.aluracursos.forohub.dtos.UserInfoDTO;
import com.aluracursos.forohub.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/user")
@Tag(name = "Registro de usuarios", description = "Ingrese sus datos para crear un nuevo usuario en el foro")
public class UserController {

    @Autowired
    private IUserService service;

    @PostMapping("/register")
    public ResponseEntity<UserInfoDTO> registerUser(@RequestBody @Valid SaveUserDTO saveUserDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        UserInfoDTO userInfoDTO = service.save(saveUserDTO);
        URI url = uriComponentsBuilder.path("/user/{id}").buildAndExpand(userInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(userInfoDTO);  //HTTP 201 Created
    }
}
