package com.codecool.classical_music_scores.repository;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComposerRepository extends JpaRepository<Composer, Long> {

    @Query("SELECT s FROM Composer c JOIN c.scores s WHERE c.id = ?1")
    List<Score> findAllScoresFromComposerById(Long id);
}
