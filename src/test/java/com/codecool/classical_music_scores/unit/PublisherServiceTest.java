package com.codecool.classical_music_scores.unit;

import com.codecool.classical_music_scores.entity.Publisher;
import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.repository.PublisherRepository;
import com.codecool.classical_music_scores.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class PublisherServiceTest {

    @Mock
    private PublisherRepository repository;

    @InjectMocks
    private PublisherService service;

    private Publisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new Publisher(1L, "Editio Musica Budapest", new ArrayList<>());
    }


    @Test
    void findAllPublishers() {
        Publisher publisher1 = new Publisher(1L, "Editio Musica Madrid", new ArrayList<>());

        given(repository.findAll()).willReturn(List.of(publisher, publisher1));

        List<Publisher> publisherList = service.findAllPublishers();

        assertThat(publisherList).isNotNull();
        assertEquals(publisherList.size(), 2);
    }

    @Test
    void findPublisherById() {
        given(repository.findById(1L)).willReturn(Optional.of(publisher));

        Publisher savedPublisher = service.findPublisherById(publisher.getId());

        assertThat(savedPublisher).isNotNull();
    }

    @Test
    void addNewPublisher() {
        given(repository.save(publisher)).willReturn(publisher);

        Publisher savedPublisher = service.addNewPublisher(publisher);

        assertThat(savedPublisher).isNotNull();
    }

    @Test
    @Disabled
    void updatePublisherById() {
        given(repository.save(publisher)).willReturn(publisher);
        publisher.setName("Paris Music Publishing");

        Publisher updatedPublisher = service.updatePublisherById(1L, publisher);

        assertEquals(updatedPublisher.getName(), "Paris Music Publishing");
    }

    @Test
    void deletePublisherById() {
        Long publisherId = 1L;

        willDoNothing().given(repository).deleteById(publisherId);

        service.deletePublisherById(publisherId);

        verify(repository, times(1)).deleteById(publisherId);
    }

    @Test
    void findAllScoresFromPublisher() {
        given(repository.save(publisher)).willReturn(publisher);

        Publisher savedPublisher = service.addNewPublisher(publisher);

        List<Score> expected = publisher.getScores();
        List<Score> actual = savedPublisher.getScores();

        assertListsHasSameElements(expected, actual);

    }

    private void assertListsHasSameElements(List<Score> expected, List<Score> actual){
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }

}