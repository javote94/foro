package com.javote.foro.repository;

import com.javote.foro.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {


    Page<Topic> findByActiveTrue(Pageable pageable);

    Optional<Topic> findByIdAndActiveTrue(Long topicId);

    Page<Topic> findByCourseIdAndActiveTrue(Long courseId, Pageable pageable);

    Page<Topic> findByCourse_Students_IdAndActiveTrue(Long studentId, Pageable pageable);

    Page<Topic> findByCourse_Moderator_IdAndActiveTrue(Long moderatorId, Pageable pageable);

}
