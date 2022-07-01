package com.codecool.classical_music_scores.controller;

import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<Score> findAllScores() {
        return scoreService.findAllScores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findScoreById(@PathVariable("id") Long id) {
        Long tryId;
        try {
            tryId = id;
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(scoreService.findScoreById(tryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/composer")
    public ResponseEntity<?> findComposerByScoreId(@PathVariable("id") Long id) {
        Long tryId;
        try {
            tryId = id;
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(scoreService.findComposerByScoreId(tryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("{id}/publisher")
    public ResponseEntity<?> findPublisherByScoreId(@PathVariable("id") Long id) {
        Long tryId;
        try {
            tryId = id;
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(scoreService.findPublisherByScoreId(tryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewScore(@RequestBody @Valid Score score, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(scoreService.addNewScore(score));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateScoreById(@RequestBody @Valid Score score, @PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(scoreService.updateScoreById(id, score));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteScoreById(@PathVariable("id") Long id) {
        scoreService.deleteScoreById(id);
    }
}
