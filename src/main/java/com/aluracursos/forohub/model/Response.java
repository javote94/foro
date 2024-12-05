package com.aluracursos.forohub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Table(name = "responses")
@Entity(name = "Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    private Boolean solution;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private Boolean active;

    public Response(String message, User author, Topic topic) {
        this.message = message;
        this.creationDate = LocalDateTime.now();
        this.solution = false;
        this.topic = topic;
        this.author = author;
        this.active = true;
    }
}
