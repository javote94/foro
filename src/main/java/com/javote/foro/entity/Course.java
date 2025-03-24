package com.javote.foro.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Table(name = "courses")
@Entity(name = "Course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "courses")
    private Set<User> students;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @OneToMany(mappedBy = "course")
    private Set<Topic> topics;

    private Boolean active;

}
