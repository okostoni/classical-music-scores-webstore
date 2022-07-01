package com.codecool.classical_music_scores.integration;

import com.codecool.classical_music_scores.testmodels.Composer;
import org.junit.jupiter.api.BeforeEach;
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

import static com.codecool.classical_music_scores.data.TestComposer.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ComposerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String entityUrl;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + port;
        entityUrl = baseUrl + "/composers";
    }

    @Test
    void emptyDatabase_getAll_shouldReturnEmptyList() {
        assertEquals(Collections.emptyList(), List.of(restTemplate.getForObject(entityUrl, Composer[].class)));
    }

    @Test
    void emptyDatabase_addOne_shouldReturnAddedComposer() {
        Composer result = restTemplate.postForObject(entityUrl, BACH, Composer.class);
        assertEquals(BACH.getName(), result.getName());
    }

    @Test
    void someComposersStored_getAll_shouldReturnAll() {
        List<Composer> testData = List.of(BACH, BEETHOVEN, VIVALDI);
        testData.forEach(i -> restTemplate.postForObject(entityUrl, i, Composer.class));

        Composer[] result = restTemplate.getForObject(entityUrl, Composer[].class);
        assertEquals(testData.size(), result.length);

        Set<String> composerNames = testData.stream().map(Composer::getName).collect(Collectors.toSet());
        assertTrue(Arrays.stream(result).map(Composer::getName).allMatch(composerNames::contains));
    }

    @Test
    void oneComposerStored_getOneById_shouldReturnCorrectComposer() {
        Long id = restTemplate.postForObject(entityUrl, BACH, Composer.class).getId();
        Composer result = restTemplate.getForObject(entityUrl + "/" + id, Composer.class);
        assertEquals(BACH.getName(), result.getName());
        assertEquals(id, result.getId());
    }

    @Test
    void someComposersStored_deleteOne_getAllShouldReturnRemaining() {
        List<Composer> testData = new ArrayList<>(List.of(BACH, BEETHOVEN, VIVALDI));
        testData = new ArrayList<>(testData.stream()
                .map(p -> restTemplate.postForObject(entityUrl, p, Composer.class))
                .toList());

        restTemplate.delete(entityUrl + "/" + testData.get(0).getId());
        Set<String> expectedComposerNames = testData.stream().skip(1L).map(Composer::getName).collect(Collectors.toSet());

        Composer[] response = restTemplate.getForObject(entityUrl, Composer[].class);

        assertEquals(expectedComposerNames.size(), response.length);
        for (Composer c : response) {
            assertTrue(expectedComposerNames.contains(c.getName()));
        }
    }

    @Test
    void oneComposerStored_deleteById_getAllShouldReturnEmptyList() {
        Composer testComposer = restTemplate.postForObject(entityUrl, ALBENIZ, Composer.class);
        assertNotNull(testComposer.getId());
        restTemplate.delete(entityUrl + "/" + testComposer.getId());
        Composer[] result = restTemplate.getForObject(entityUrl, Composer[].class);
        assertEquals(0, result.length);
    }

    @Test
    void oneComposerStored_updateIt_ComposerShouldBeUpdated() {
        Composer testComposer = restTemplate.postForObject(entityUrl, BEETHOVEN, Composer.class);

        String url = entityUrl + "/" + testComposer.getId();
        testComposer.setName(testComposer.getName() + "update");
        restTemplate.put(url, testComposer);

        Composer result = restTemplate.getForObject(url, Composer.class);
        assertEquals(testComposer.getName(), result.getName());
    }

    @Test
    void oneComposerStored_updateWithWrongId_ComposerShouldBeUnchanged() {
        Composer testComposer = restTemplate.postForObject(entityUrl, BEETHOVEN, Composer.class);

        String originalName = testComposer.getName();;
        assertNotNull(originalName);
        Long originalId = testComposer.getId();

        testComposer.setName(originalName + "update");
        testComposer.setId(42L);
        String url = entityUrl + "/" + originalId;
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(testComposer, null), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());

        Composer result = restTemplate.getForObject(url, Composer.class);
        assertEquals(originalName, result.getName());
    }

    @Test
    void getOneByWrongId_shouldRespond404() {
        ResponseEntity<String> response = restTemplate.getForEntity(entityUrl + "/12345", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void postInvalidComposerWithNull_shouldRespond400() {
        ResponseEntity<String> response = restTemplate.postForEntity(entityUrl,
                new Composer(null, null, null, null, new ArrayList<>()), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void postInvalidComposerWithBlankString_shouldRespond400() {
        ResponseEntity<String> response = restTemplate.postForEntity(entityUrl,
                new Composer(null, "", null, null, new ArrayList<>()), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void putInvalidComposerWithBlankString_shouldRespond400() {
        Composer testComposer = restTemplate.postForObject(entityUrl, BEETHOVEN, Composer.class);
        String url = entityUrl + "/" + testComposer.getId();

        testComposer.setName("");
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(testComposer, null), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }
}

