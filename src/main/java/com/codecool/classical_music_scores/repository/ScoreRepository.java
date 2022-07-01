package com.codecool.classical_music_scores.repository;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Publisher;
import com.codecool.classical_music_scores.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT c FROM Score s JOIN s.composer c WHERE s.id = ?1")
    Composer findComposerById(Long id);

    @Query("SELECT p FROM Score s JOIN s.publisher p WHERE s.id = ?1")
    Publisher findPublisherByScoreId(Long id);

}
