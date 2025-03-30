package com.javote.foro.repository;

import com.javote.foro.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

    Optional<Response> findByIdAndActiveTrue(Long responseId);

    boolean existsByTopicIdAndSolutionTrue(Long topicId);

    @Query("""
    SELECT COUNT(r) > 0
    FROM Response r
    WHERE r.topic.id = :topicId
    AND r.solution = true
    AND r.active = true
    AND r.id <> :currentResponseId
    """)
    boolean existsAnotherSolutionInTopic(@Param("topicId") Long topicId,
                                         @Param("currentResponseId") Long currentResponseId);


}
