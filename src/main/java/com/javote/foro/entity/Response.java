package com.javote.foro.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Table(name = "responses")
@Entity(name = "Response")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
