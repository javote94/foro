package com.javote.foro.repository;

import com.javote.foro.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findByActiveTrue(Pageable pageable);

    @Query("""
            SELECT t
            FROM Topic t
            WHERE t.active = true
            AND t.course.id = :courseId
            """)
    Page<Topic> findTopicsByCourseId(@Param("courseId") Long courseId, Pageable pageable);

    @Query("""
        SELECT t
        FROM Topic t
        WHERE t.active = true
        AND t.course.id IN (
            SELECT c.id
            FROM Course c
            JOIN c.students s
            WHERE s.id = :studentId
        )
        """)
    Page<Topic> findTopicsByEnrolledCourses(@Param("studentId") Long studentId, Pageable pageable);


    @Query("""
        SELECT t
        FROM Topic t
        WHERE t.active = true
        AND t.course.id IN (
            SELECT c.id
            FROM Course c
            WHERE c.moderator.id = :moderatorId
        )
        """)
    Page<Topic> findTopicsByModeratedCourses(@Param("moderatorId") Long moderatorId, Pageable pageable);

}
