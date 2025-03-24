package com.javote.foro.repository;

import com.javote.foro.entity.Course;
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
            WHERE c.id = :courseId
            AND c.moderator.id = :userId
            AND c.active = true
            """)
    boolean isModeratorOfCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    @Query("""
            SELECT COUNT(c) > 0
            FROM Course c
            JOIN c.students st
            WHERE st.id = :userId
            AND c.id = :courseId
            AND c.active = true
            AND st.active = true
            """)
    boolean isAStudentOfTheCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
