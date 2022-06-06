package com.codecool.classical_music_scores.service;

import com.codecool.classical_music_scores.repository.ComposerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComposerService {

    private final ComposerRepository composerRepository;

    @Autowired
    public ComposerService(ComposerRepository composerRepository) {
        this.composerRepository = composerRepository;
    }
}
