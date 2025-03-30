package com.javote.foro.controller;

import com.javote.foro.dto.SaveUserDTO;
import com.javote.foro.dto.UserInfoDTO;
import com.javote.foro.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/users")
@Tag(name = "User Registration", description = "Public endpoint to register new students in the forum.")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    // POST http://localhost:8080/users
    @PostMapping
    @Operation(summary = "Register a new student", description = "Creates a new user with the USER role. No authentication required.")
    public ResponseEntity<UserInfoDTO> registerStudent(@RequestBody @Valid SaveUserDTO saveUserDTO,
                                                UriComponentsBuilder uriComponentsBuilder) {
        UserInfoDTO userInfoDTO = userService.saveStudent(saveUserDTO);
        URI url = uriComponentsBuilder.path("/user/{id}").buildAndExpand(userInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(userInfoDTO);  //HTTP 201 Created
    }
}
