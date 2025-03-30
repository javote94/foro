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
@Tag(name = "Courses", description = "Endpoints for course creation, enrollment and deletion.")
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    // POST http://localhost:8080/courses
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new course", description = "Only administrators can create new courses by assigning a moderator to each course.")
    public ResponseEntity<CourseInfoDTO> createCourse(@RequestBody @Valid SaveCourseDTO saveCourseDTO,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        CourseInfoDTO courseInfoDTO = courseService.createCourse(saveCourseDTO);
        URI url = uriComponentsBuilder.path("/course/{id}").buildAndExpand(courseInfoDTO.id()).toUri();
        return ResponseEntity.created(url).body(courseInfoDTO);  //HTTP 201 Created
    }

    // PATCH http://localhost:8080/courses/{courseId}
    @PatchMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Operation(summary = "Enroll a student in a course", description = "Allows moderators (for their courses) or administrators to add students to a course.")
    public ResponseEntity<CourseInfoDTO> addStudent(@PathVariable Long courseId,
                                                    @RequestBody UpdateCourseDTO updateCourseDTO){
        CourseInfoDTO courseInfoDTO = courseService.addStudent(courseId, updateCourseDTO);
        return ResponseEntity.ok(courseInfoDTO);
    }

    // DELETE http://localhost:8080/courses/{courseId}
    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Logically delete a course", description = "Only administrators can deactivate a course. Topics and responses associated with the course will also be deactivated.")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
