package com.codecool.classical_music_scores.integration;

import com.codecool.classical_music_scores.testmodels.Composer;
import com.codecool.classical_music_scores.testmodels.Publisher;
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

import static com.codecool.classical_music_scores.data.TestPublisher.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class PublisherIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String entityUrl;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + port;
        entityUrl = baseUrl + "/publishers";
    }

    @Test
    void emptyDatabase_getAll_shouldReturnEmptyList() {
        assertEquals(Collections.emptyList(), List.of(restTemplate.getForObject(entityUrl, Publisher[].class)));
    }

    @Test
    void emptyDatabase_addOne_shouldReturnAddedPublisher() {
        Publisher result = restTemplate.postForObject(entityUrl, BERLIN, Publisher.class);
        assertEquals(BERLIN.getName(), result.getName());
    }

    @Test
    void somePublishersStored_getAll_shouldReturnAll() {
        List<Publisher> testData = List.of(BERLIN, ROME, MADRID);
        testData.forEach(i -> restTemplate.postForObject(entityUrl, i, Publisher.class));

        Publisher[] result = restTemplate.getForObject(entityUrl, Publisher[].class);
        assertEquals(testData.size(), result.length);

        Set<String> publisherNames = testData.stream().map(Publisher::getName).collect(Collectors.toSet());
        assertTrue(Arrays.stream(result).map(Publisher::getName).allMatch(publisherNames::contains));
    }

    @Test
    void onePublisherStored_getOneById_shouldReturnCorrectPublisher() {
        Long id = restTemplate.postForObject(entityUrl, ROME, Publisher.class).getId();
        Publisher result = restTemplate.getForObject(entityUrl + "/" + id, Publisher.class);
        assertEquals(ROME.getName(), result.getName());
        assertEquals(id, result.getId());
    }

    @Test
    void somePublishersStored_deleteOne_getAllShouldReturnRemaining() {
        List<Publisher> testData = new ArrayList<>(List.of(BERLIN, ROME, MADRID));
        testData = new ArrayList<>(testData.stream()
                .map(p -> restTemplate.postForObject(entityUrl, p, Publisher.class))
                .toList());

        restTemplate.delete(entityUrl + "/" + testData.get(0).getId());
        Set<String> expectedPublisherNames = testData.stream().skip(1L).map(Publisher::getName).collect(Collectors.toSet());

        Publisher[] response = restTemplate.getForObject(entityUrl, Publisher[].class);

        assertEquals(expectedPublisherNames.size(), response.length);
        for (Publisher p : response) {
            assertTrue(expectedPublisherNames.contains(p.getName()));
        }
    }

    @Test
    void onePublisherStored_deleteById_getAllShouldReturnEmptyList() {
        Publisher testPublisher = restTemplate.postForObject(entityUrl, MADRID, Publisher.class);
        assertNotNull(testPublisher.getId());
        restTemplate.delete(entityUrl + "/" + testPublisher.getId());
        Publisher[] result = restTemplate.getForObject(entityUrl, Publisher[].class);
        assertEquals(0, result.length);
    }

    @Test
    void onePublisherStored_updateIt_PublisherShouldBeUpdated() {
        Publisher testPublisher = restTemplate.postForObject(entityUrl, ROME, Publisher.class);

        String url = entityUrl + "/" + testPublisher.getId();
        testPublisher.setName(testPublisher.getName() + "update");
        restTemplate.put(url, testPublisher);

        Composer result = restTemplate.getForObject(url, Composer.class);
        assertEquals(testPublisher.getName(), result.getName());
    }

    @Test
    void onePublisherStored_updateWithWrongId_PublisherShouldBeUnchanged() {
        Publisher testPublisher = restTemplate.postForObject(entityUrl, BERLIN, Publisher.class);

        String originalName = testPublisher.getName();
        assertNotNull(originalName);
        Long originalId = testPublisher.getId();

        testPublisher.setName(originalName + "update");
        testPublisher.setId(42L);
        String url = entityUrl + "/" + originalId;
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(testPublisher, null), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());

        Publisher result = restTemplate.getForObject(url, Publisher.class);
        assertEquals(originalName, result.getName());
    }

    @Test
    void getOneByWrongId_shouldRespond404() {
        ResponseEntity<String> response = restTemplate.getForEntity(entityUrl + "/12345", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void postInvalidPublisherWithNull_shouldRespond400() {
        ResponseEntity<String> response = restTemplate.postForEntity(entityUrl,
                new Publisher(null, null, new ArrayList<>()), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void postInvalidPublisherWithBlankString_shouldRespond400() {
        ResponseEntity<String> response = restTemplate.postForEntity(entityUrl,
                new Publisher(null, "", new ArrayList<>()), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void putInvalidPublisherWithBlankString_shouldRespond400() {
        Publisher testPublisher = restTemplate.postForObject(entityUrl, MADRID, Publisher.class);
        String url = entityUrl + "/" + testPublisher.getId();

        testPublisher.setName("");
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(testPublisher, null), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }
}

