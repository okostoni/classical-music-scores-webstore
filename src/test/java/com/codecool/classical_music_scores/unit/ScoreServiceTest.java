package com.codecool.classical_music_scores.unit;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Publisher;
import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.entity.type.InstrumentType;
import com.codecool.classical_music_scores.repository.ScoreRepository;
import com.codecool.classical_music_scores.service.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository;

    @InjectMocks
    private ScoreService service;

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score(1L, "Test Piece in A Major",
                new Composer(null, "Testing Thomas", 1969, 2060, new ArrayList<>()),
                InstrumentType.GUITAR, 2022, 9000.00,
                new Publisher(null, "Tester Edition Press", new ArrayList<>()), true);
    }

    @DisplayName("JUnit test for findAllScores method")
    @Test
    void testFindAllScores() {
        Score score1 = new Score(2L, "Test Piece in B Major",
                new Composer(null, "Testing Tamara", 1979, 2060, new ArrayList<>()),
                InstrumentType.ORGAN, 2022, 9000.00,
                new Publisher(null, "Tester Edition Press France", new ArrayList<>()), true);

        given(scoreRepository.findAll()).willReturn(List.of(score, score1));

        List<Score> scoreList = service.findAllScores();

        assertThat(scoreList).isNotNull();
        assertEquals(scoreList.size(), 2);
    }

    @DisplayName("JUnit test for findScoreById method")
    @Test
    void testFindScoreById() {
        given(scoreRepository.findById(1L)).willReturn(Optional.of(score));

        Score savedScore = service.findScoreById(score.getId());

        assertThat(savedScore).isNotNull();
    }

    @DisplayName("JUnit test for addNewScore method")
    @Test
    void testAddNewScore_postMethod() {
        given(scoreRepository.save(score)).willReturn(score);

        Score savedScore = service.addNewScore(score);

        assertThat(savedScore).isNotNull();
    }

    @DisplayName("JUnit test for updateEmployee method")
    @Test
    @Disabled
    void testUpdateScoreById() {
        given(scoreRepository.save(score)).willReturn(score);
        score.setPrice(4444.44);
        score.setTitle("New Title");

        Score updatedScore = service.updateScoreById(1L, score);

        assertEquals(updatedScore.getTitle(), "New Title");
        assertEquals(updatedScore.getPrice(), 4444.44);
    }

    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    void deleteScoreById() {
        Long scoreId = 1L;

        willDoNothing().given(scoreRepository).deleteById(scoreId);

        service.deleteScoreById(scoreId);

        verify(scoreRepository, times(1)).deleteById(scoreId);
    }


}