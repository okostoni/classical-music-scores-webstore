package com.codecool.classical_music_scores.service;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Publisher;
import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow();
    }

    public void addNewPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void updatePublisherById(Long id, Publisher publisher) {
        Publisher selectedPublisher = findPublisherById(id);
        selectedPublisher.setName(publisher.getName());
        selectedPublisher.setScores(publisher.getScores());
        publisherRepository.save(selectedPublisher);
    }

    public void deletePublisherById(Long id) {
        publisherRepository.deleteById(id);
    }

    public List<Score> findAllScoresFromPublisher(Long id) {
        return publisherRepository.findAllScoresFromPublisher(id);
    }

    public List<Composer> findAllComposersFromPublisher(Long id) {
        return publisherRepository.findAllComposersFromPublisher(id);
    }
}
