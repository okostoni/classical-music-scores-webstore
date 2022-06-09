package com.codecool.classical_music_scores.service;

import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<Score> findAllScores() {
        return scoreRepository.findAll();
    }

    public Score findScoreById(Long id) {
        return scoreRepository.findById(id).orElseThrow();
    }

    public void addNewScore(Score score) {
        scoreRepository.save(score);
    }

    public void updateScoreById(Long id, Score score) {
        Score selectedScore = findScoreById(id);
        selectedScore.setComposer(score.getComposer());
        selectedScore.setAvailableInStock(score.isAvailableInStock());
        selectedScore.setPrice(score.getPrice());
        selectedScore.setPublisher(score.getPublisher());
        selectedScore.setInstrumentType(score.getInstrumentType());
        selectedScore.setTitle(score.getTitle());
        selectedScore.setYearOfCreation(score.getYearOfCreation());
    }

    public void deleteScoreById(Long id) {
        scoreRepository.deleteById(id);
    }
}
