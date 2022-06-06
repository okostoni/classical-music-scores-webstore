package com.codecool.classical_music_scores.repository;

import com.codecool.classical_music_scores.entity.Composer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposerRepository extends JpaRepository<Composer, Long> {

}
