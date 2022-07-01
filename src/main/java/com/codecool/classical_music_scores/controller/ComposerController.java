package com.codecool.classical_music_scores.controller;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.service.ComposerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> findComposerById(@PathVariable("id") Long id) {
        Long tryId;
        try {
            tryId = id;
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(composerService.findComposerById(tryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/scores")
    public ResponseEntity<?> findAllScoresFromComposerById(@PathVariable("id") Long id) {
        Long tryId;
        try {
            tryId = id;
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(composerService.findAllScoresFromComposerById(tryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewComposer(@RequestBody @Valid Composer composer, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(composerService.addNewComposer(composer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComposerById(@PathVariable("id") Long id, @RequestBody @Valid Composer composer) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(composerService.updateComposer(id, composer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("{id}")
    public void deleteComposerById(@PathVariable("id") Long id) {
        composerService.deleteComposer(id);
    }
}
