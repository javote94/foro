package com.javote.foro.repository;

import com.javote.foro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("""
            SELECT u
            FROM User u
            WHERE u.id = :moderatorId
            AND
            u.profile = MODERATOR
            AND
            u.active = true""")
    Optional<User> findModeratorById(@Param("moderatorId") Long moderatorId);

    @Query("""
            SELECT u
            FROM User u
            WHERE u.id = :userId
            AND u.profile = USER
            AND u.active = true
            """)
    Optional<User> findStudentById(@Param("userId") Long userId);

}
