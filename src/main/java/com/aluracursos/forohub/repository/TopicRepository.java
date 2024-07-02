package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.model.Topic;
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
            WHERE
            t.active = true
            AND
            t.course.name = :courseName
            AND
            YEAR(t.creationDate) = :year""")
    Page<Topic> findByCourseNameAndYear(@Param("courseName") String courseName, @Param("year") int year, Pageable pageable);

    @Query("""
            SELECT t
            FROM Topic t
            WHERE
            t.active = true
            AND
            t.course.name = :courseName
            """)
    Page<Topic> findByCourseName(@Param("courseName") String courseName, Pageable pageable);

    @Query("""
            SELECT t
            FROM Topic t
            WHERE
            t.active = true
            AND
            YEAR(t.creationDate) = :year
            """)
    Page<Topic> findByYear(@Param("year") int year, Pageable pageable);

}
