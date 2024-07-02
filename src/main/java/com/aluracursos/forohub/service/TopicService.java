package com.aluracursos.forohub.service;

import com.aluracursos.forohub.dtos.SaveTopicDTO;
import com.aluracursos.forohub.dtos.TopicInfoDTO;
import com.aluracursos.forohub.dtos.UpdateTopicDTO;
import com.aluracursos.forohub.exceptions.TopicNotFoundException;
import com.aluracursos.forohub.model.Course;
import com.aluracursos.forohub.model.Topic;
import com.aluracursos.forohub.model.User;
import com.aluracursos.forohub.repository.CourseRepository;
import com.aluracursos.forohub.repository.TopicRepository;
import com.aluracursos.forohub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicService implements ITopicService{

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional
    public TopicInfoDTO createTopic(SaveTopicDTO saveTopicDTO) {
        User author = userRepository.getReferenceById(Long.valueOf(saveTopicDTO.idAuthor()));
        Course course = courseRepository.findByName(saveTopicDTO.courseName())
                .orElseThrow(() -> new RuntimeException("No existe un curso con el nombre dado"));
        Topic topic = topicRepository.save(
                new Topic(saveTopicDTO.title(), saveTopicDTO.message(), author, course)
        );
        return new TopicInfoDTO(topic);
    }

    @Override
    public Page<TopicInfoDTO> listTopics(String courseName, Integer year, Pageable pageable) {

        Page<Topic> topicPage;
        if (courseName != null && year != null) {
            topicPage = topicRepository.findByCourseNameAndYear(courseName, year, pageable);
        } else if (courseName != null) {
            topicPage = topicRepository.findByCourseName(courseName, pageable);
        } else if (year != null) {
            topicPage = topicRepository.findByYear(year, pageable);
        } else {
            topicPage = topicRepository.findByActiveTrue(pageable);
        }
        return topicPage.map(TopicInfoDTO::new);
    }

    @Override
    public TopicInfoDTO getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + id + " not found"));
        return new TopicInfoDTO(topic);
    }

    @Override
    @Transactional
    public TopicInfoDTO updateTopic(Long id, UpdateTopicDTO updateTopicDTO) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + id + " not found"));

        if (updateTopicDTO.title() != null) {
            if (updateTopicDTO.title().isBlank() || updateTopicDTO.title().length() < 10) {
                throw new IllegalArgumentException("Title must be at least 10 characters long and cannot be blank");
            }
            topic.setTitle(updateTopicDTO.title());
        }
        if (updateTopicDTO.message() != null) {
            if (updateTopicDTO.message().isBlank() || updateTopicDTO.message().length() < 10) {
                throw new IllegalArgumentException("Message must be at least 10 characters long and cannot be blank");
            }
            topic.setMessage(updateTopicDTO.message());
        }
        if (updateTopicDTO.status() != null) {
            topic.setStatus(updateTopicDTO.status());
        }
        // No es necesario llamar a topicRepository.save(topic) aquí
        return new TopicInfoDTO(topic);
    }

    @Override
    @Transactional
    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + id + " not found"));
        topic.setActive(false);
    }

}
