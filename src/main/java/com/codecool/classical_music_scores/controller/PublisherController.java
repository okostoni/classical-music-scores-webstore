package com.codecool.classical_music_scores.controller;

import com.codecool.classical_music_scores.entity.Publisher;
import com.codecool.classical_music_scores.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> findPublisherById(@PathVariable("id") Long id) {
        Long tryId;
        try {
            tryId = id;
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(publisherService.findPublisherById(tryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/scores")
    public ResponseEntity<?> findAllScoresFromPublisher(@PathVariable("id") Long id) {
        Long tryId;
        try {
            tryId = id;
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(publisherService.findAllScoresFromPublisher(tryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/composers")
    public ResponseEntity<?> findAllComposersFromPublisher(@PathVariable("id") Long id) {
        Long tryId;
        try {
            tryId = id;
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(publisherService.findAllComposersFromPublisher(tryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewPublisher(@RequestBody @Valid Publisher publisher, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(publisherService.addNewPublisher(publisher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisherById(@PathVariable("id") Long id, @RequestBody @Valid Publisher publisher) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(publisherService.updatePublisherById(id, publisher));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePublisherById(@PathVariable("id") Long id) {
        publisherService.deletePublisherById(id);
    }
}
