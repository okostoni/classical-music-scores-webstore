package com.codecool.classical_music_scores.controller;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Publisher;
import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Publisher> findAllPublishers() {
        return publisherService.findAllPublishers();
    }

    @GetMapping("/{id}")
    public Publisher findPublisherById(@PathVariable("id") Long id) {
        return publisherService.findPublisherById(id);
    }

    @GetMapping("/{id}/scores")
    public List<Score> findAllScoresFromPublisher(@PathVariable("id") Long id) {
        return publisherService.findAllScoresFromPublisher(id);
    }

    @PostMapping
    public void addNewPublisher(@RequestBody Publisher publisher) {
        publisherService.addNewPublisher(publisher);
    }

    @PutMapping("/{id}")
    public void updatePublisherById(@PathVariable("id") Long id, @RequestBody Publisher publisher) {
        publisherService.updatePublisherById(id, publisher);
    }

    @DeleteMapping("/{id}")
    public void deletePublisherById(@PathVariable("id") Long id) {
        publisherService.deletePublisherById(id);
    }
}
