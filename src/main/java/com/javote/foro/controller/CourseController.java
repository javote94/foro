package com.javote.foro.controller;

import com.javote.foro.dto.CourseInfoDTO;
import com.javote.foro.dto.SaveCourseDTO;
import com.javote.foro.dto.UpdateCourseDTO;
import com.javote.foro.service.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Cursos", description = "Registrar nuevos cursos")
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    // POST http://localhost:8080/courses
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Crea un curso")
    public ResponseEntity<CourseInfoDTO> createCourse(@RequestBody @Valid SaveCourseDTO saveCourseDTO,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        CourseInfoDTO courseInfoDTO = courseService.createCourse(saveCourseDTO);
        URI url = uriComponentsBuilder.path("/course/{id}").buildAndExpand(courseInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(courseInfoDTO);  //HTTP 201 Created
    }

    @PatchMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Operation(summary = "Agrega un estudiante al curso")
    public ResponseEntity<CourseInfoDTO> addStudent(@PathVariable Long courseId,
                                                    @RequestBody UpdateCourseDTO updateCourseDTO){
        CourseInfoDTO courseInfoDTO = courseService.addStudent(courseId, updateCourseDTO);
        return ResponseEntity.ok(courseInfoDTO);
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Elimina un curso")
    public ResponseEntity deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
