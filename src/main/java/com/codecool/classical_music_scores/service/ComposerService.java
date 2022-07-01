package com.codecool.classical_music_scores.service;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.repository.ComposerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComposerService {

    private final ComposerRepository composerRepository;

    @Autowired
    public ComposerService(ComposerRepository composerRepository) {
        this.composerRepository = composerRepository;
    }

    public List<Composer> findAllComposers() {
        return composerRepository.findAll();
    }

    public Composer findComposerById(Long id) {
        return composerRepository.findById(id).orElseThrow();
    }

    public Composer addNewComposer(Composer composer) {
        return composerRepository.save(composer);
    }

    public Composer updateComposer(Long id, Composer composer) {
        Composer selectedComposer = findComposerById(id);
        if (selectedComposer != null) {
            selectedComposer.setId(composer.getId());
            selectedComposer.setName(composer.getName());
            selectedComposer.setYearOfBirth(composer.getYearOfBirth());
            selectedComposer.setYearOfDeath(composer.getYearOfDeath());
            selectedComposer.setScores(composer.getScores());
            composerRepository.save(selectedComposer);
            return selectedComposer;
        }
        throw new RuntimeException();
    }

    public void deleteComposer(Long id) {
        composerRepository.deleteById(id);
    }

    public List<Score> findAllScoresFromComposerById(Long id) {
        return composerRepository.findAllScoresFromComposerById(id);
    }
}
