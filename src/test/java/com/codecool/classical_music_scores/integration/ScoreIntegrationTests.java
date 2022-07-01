package com.codecool.classical_music_scores.integration;

import com.codecool.classical_music_scores.testmodels.Composer;
import com.codecool.classical_music_scores.testmodels.InstrumentType;
import com.codecool.classical_music_scores.testmodels.Publisher;
import com.codecool.classical_music_scores.testmodels.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.Collectors;

import static com.codecool.classical_music_scores.data.TestScore.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ScoreIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String entityUrl;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + port;
        entityUrl = baseUrl + "/scores";
    }

    @Test
    void emptyDatabase_getAll_shouldReturnEmptyList() {
        assertEquals(Collections.emptyList(), List.of(restTemplate.getForObject(entityUrl, Score[].class)));
    }

    @Test
    void emptyDatabase_addOne_shouldReturnAddedScore() {
        Score result = restTemplate.postForObject(entityUrl, SONATA, Score.class);
        assertEquals(SONATA.getTitle(), result.getTitle());
    }

    @Test
    void someScoresStored_getAll_shouldReturnAll() {
        List<Score> testData = List.of(SONATA, PRELUDE_AND_FUGUE, CONCERTO);
        testData.forEach(i -> restTemplate.postForObject(entityUrl, i, Score.class));

        Score[] result = restTemplate.getForObject(entityUrl, Score[].class);
        assertEquals(testData.size(), result.length);

        Set<String> scoreNames = testData.stream().map(Score::getTitle).collect(Collectors.toSet());
        assertTrue(Arrays.stream(result).map(Score::getTitle).allMatch(scoreNames::contains));
    }

    @Test
    void oneScoreStored_getOneById_shouldReturnCorrectScore() {
        Long id = restTemplate.postForObject(entityUrl, SONATA, Score.class).getId();
        Score result = restTemplate.getForObject(entityUrl + "/" + id, Score.class);
        assertEquals(SONATA.getTitle(), result.getTitle());
        assertEquals(id, result.getId());
    }

    @Test
    void someScoresStored_deleteOne_getAllShouldReturnRemaining() {
        List<Score> testData = new ArrayList<>(List.of(SONATA, PRELUDE_AND_FUGUE, ASTURIAS));
        testData = new ArrayList<>(testData.stream()
                .map(p -> restTemplate.postForObject(entityUrl, p, Score.class))
                .toList());

        restTemplate.delete(entityUrl + "/" + testData.get(0).getId());
        Set<String> expectedScoreTitles = testData.stream().skip(1L).map(Score::getTitle).collect(Collectors.toSet());

        Score[] response = restTemplate.getForObject(entityUrl, Score[].class);

        assertEquals(expectedScoreTitles.size(), response.length);
        for (Score p : response) {
            assertTrue(expectedScoreTitles.contains(p.getTitle()));
        }
    }

    @Test
    void oneScoreStored_deleteById_getAllShouldReturnEmptyList() {
        Score testScore = restTemplate.postForObject(entityUrl, SUITE, Score.class);
        assertNotNull(testScore.getId());
        restTemplate.delete(entityUrl + "/" + testScore.getId());
        Score[] result = restTemplate.getForObject(entityUrl, Score[].class);
        assertEquals(0, result.length);
    }

    @Test
    void oneScoreStoredUsedInComposer_deleteById_ScoreShouldNotBeDeleted() {
        Score testScore = restTemplate.postForObject(entityUrl, CONCERTO, Score.class);
        Composer testComposer = restTemplate.postForObject(
                "http://localhost:" + port + "/composers",
                new Composer(null, "Wolfgang Amadeus Mozart", 1756, 1792, List.of(testScore)),
                Composer.class
        );
        restTemplate.delete(entityUrl + "/" + testScore.getId());
        Score result = restTemplate.getForObject(entityUrl + "/" + testScore.getId(), Score.class);
        assertEquals(testScore.getTitle(), result.getTitle());
    }

    @Test
    void oneScoreStoredUsedInPublisher_deleteById_ScoreShouldNotBeDeleted() {
        Score testScore = restTemplate.postForObject(entityUrl, PRELUDE_AND_FUGUE, Score.class);
        Publisher testPublisher = restTemplate.postForObject(
                "http://localhost:" + port + "/publishers",
                new Publisher(null, "French Music Press", List.of(testScore)),
                Publisher.class
        );
        restTemplate.delete(entityUrl + "/" + testScore.getId());
        Score result = restTemplate.getForObject(entityUrl + "/" + testScore.getId(), Score.class);
        assertEquals(testScore.getTitle(), result.getTitle());
    }

    @Test
    @Disabled
    void oneScoreStored_updateIt_ScoreShouldBeUpdated() {
        Score testScore = restTemplate.postForObject(entityUrl, PRELUDE_AND_FUGUE, Score.class);

        String url = entityUrl + "/" + testScore.getId();
        testScore.setTitle(testScore.getTitle() + "update");
        restTemplate.put(url, testScore);

        Score result = restTemplate.getForObject(url, Score.class);
        assertEquals(testScore.getTitle(), result.getTitle());
    }

    @Test
    void oneScoreStored_updateWithWrongId_ScoreShouldBeUnchanged() {
        Score testScore = restTemplate.postForObject(entityUrl, ASTURIAS, Score.class);

        String originalTitle = testScore.getTitle();
        assertNotNull(originalTitle);
        Long originalId = testScore.getId();

        testScore.setTitle(originalTitle + "update");
        testScore.setId(42L);
        String url = entityUrl + "/" + originalId;
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(testScore, null), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());

        Score result = restTemplate.getForObject(url, Score.class);
        assertEquals(originalTitle, result.getTitle());
    }

    @Test
    void getOneByWrongId_shouldRespond404() {
        ResponseEntity<String> response = restTemplate.getForEntity(entityUrl + "/12345", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void postInvalidScoreWithNull_shouldRespond400() {
        ResponseEntity<String> response = restTemplate.postForEntity(entityUrl,
                new Score(null, null, null, null,
                        null, null, null, null), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void postInvalidScoreWithBlankString_shouldRespond400() {
        ResponseEntity<String> response = restTemplate.postForEntity(entityUrl,
                new Score(null, "", null, InstrumentType.PIANO,
                        null, null, null, null), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void putInvalidScoreWithBlankString_shouldRespond400() {
        Score testScore = restTemplate.postForObject(entityUrl, SUITE, Score.class);
        String url = entityUrl + "/" + testScore.getId();

        testScore.setTitle("");
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(testScore, null), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }
}
