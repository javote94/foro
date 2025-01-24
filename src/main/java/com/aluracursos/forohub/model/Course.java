package com.aluracursos.forohub.model;

import com.aluracursos.forohub.dtos.SaveCourseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Table(name = "courses")
@Entity(name = "Course")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<User> students;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @OneToMany(mappedBy = "course")
    private List<Topic> topics;

    private Boolean active;

    public Course(SaveCourseDTO saveCourseDTO) {
        this.name = saveCourseDTO.name();
        this.active = true;
    }
}
