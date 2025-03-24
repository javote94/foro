package com.javote.foro.repository;

import com.javote.foro.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

    Optional<Response> findByIdAndActiveTrue(Long responseId);

    boolean existsByTopicIdAndSolutionTrue(Long topicId);


}
