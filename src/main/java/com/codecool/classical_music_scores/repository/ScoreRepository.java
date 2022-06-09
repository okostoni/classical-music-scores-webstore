package com.codecool.classical_music_scores.repository;

import com.codecool.classical_music_scores.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

}