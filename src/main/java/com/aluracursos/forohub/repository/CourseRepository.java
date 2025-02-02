package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

    @Query("""
            SELECT COUNT(c) > 0
            FROM Course c
            JOIN c.topics t
            WHERE t.id = :topicId
              AND c.moderator.id = :moderatorId
              AND c.active = true
            """)
    boolean existsByTopicIdAndModeratorId(@Param("topicId") Long topicId, @Param("moderatorId") Long moderatorId);
}
