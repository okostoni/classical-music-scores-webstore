package com.codecool.classical_music_scores.controller;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.service.ComposerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/composers")
public class ComposerController {

    private final ComposerService composerService;

    @Autowired
    public ComposerController(ComposerService composerService) {
        this.composerService = composerService;
    }

    @GetMapping
    public List<Composer> findAllComposers() {
        return composerService.findAllComposers();
    }

    @GetMapping("/{id}")
    public Composer findComposerById(@PathVariable("id") Long id) {
        return composerService.findComposerById(id);
    }

    @GetMapping("/{id}/scores")
    public List<Score> findAllScoresFromComposerById(@PathVariable("id") Long id) {
        return composerService.findAllScoresFromComposerById(id);
    }

    @PostMapping
    public void addNewComposer(@RequestBody Composer composer) {
        composerService.addNewComposer(composer);
    }

    @PutMapping("/{id}")
    public void updateComposerById(@PathVariable("id") Long id, @RequestBody Composer composer) {
        composerService.updateComposer(id, composer);
    }

    @DeleteMapping("{id}")
    public void deleteComposerById(@PathVariable("id") Long id) {
        composerService.deleteComposer(id);
    }
}
