package com.codecool.classical_music_scores.service;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Publisher;
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

    public Score addNewScore(Score score) {
        return scoreRepository.save(score);
    }

    public Score updateScoreById(Long id, Score score) {
        Score selectedScore = scoreRepository.findById(id).orElseThrow();
        selectedScore.setId(score.getId());
        selectedScore.setTitle(score.getTitle());
        selectedScore.setYearOfCreation(score.getYearOfCreation());
        selectedScore.setPrice(score.getPrice());
        selectedScore.setInstrumentType(score.getInstrumentType());
        selectedScore.setIsAvailableInStock(score.getIsAvailableInStock());
        scoreRepository.save(selectedScore);
        return selectedScore;
    }

    public void deleteScoreById(Long id) {
        scoreRepository.deleteById(id);
    }

    public Composer findComposerByScoreId(Long id) {
        return scoreRepository.findComposerById(id);
    }

    public Publisher findPublisherByScoreId(Long id) {
        return scoreRepository.findPublisherByScoreId(id);
    }
}
