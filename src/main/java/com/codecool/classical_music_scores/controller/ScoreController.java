package com.codecool.classical_music_scores.controller;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Publisher;
import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Score findScoreById(@PathVariable("id") Long id) {
        return scoreService.findScoreById(id);
    }

    @GetMapping("/{id}/composer")
    public Composer findComposerByScoreId(@PathVariable("id") Long id) {
        return scoreService.findComposerByScoreId(id);
    }

    @GetMapping("{id}/publisher")
    public Publisher findPublisherByScoreId(@PathVariable("id") Long id) {
        return scoreService.findPublisherByScoreId(id);
    }

    @PostMapping
    public void addNewScore(@RequestBody Score score) {
        scoreService.addNewScore(score);
    }

    @PutMapping("/{id}")
    public void updateScoreById(@PathVariable("id") Long id, @RequestBody Score score) {
        scoreService.updateScoreById(id, score);
    }

    @DeleteMapping("/{id}")
    public void deleteScoreById(@PathVariable("id") Long id) {
        scoreService.deleteScoreById(id);
    }
}
