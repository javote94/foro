package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.dtos.CourseInfoDTO;
import com.aluracursos.forohub.dtos.SaveCourseDTO;
import com.aluracursos.forohub.service.ICourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/course")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Cursos", description = "Registrar nuevos cursos (en proceso de elaboración)")
public class CourseController {

    @Autowired
    private ICourseService service;

    // POST: http://localhost:8080/course/create
    @PostMapping("/create")
    public ResponseEntity<CourseInfoDTO> createCourse(@RequestBody @Valid SaveCourseDTO saveCourseDTO,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        CourseInfoDTO courseInfoDTO = service.create(saveCourseDTO);
        URI url = uriComponentsBuilder.path("/course/{id}").buildAndExpand(courseInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(courseInfoDTO);  //HTTP 201 Created
    }

}
