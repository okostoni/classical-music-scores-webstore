package com.codecool.classical_music_scores.repository;

import com.codecool.classical_music_scores.entity.Publisher;
import com.codecool.classical_music_scores.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("SELECT s FROM Publisher p JOIN p.scores s WHERE p.id = ?1")
    List<Score> findAllScoresFromPublisher(Long id);
}
