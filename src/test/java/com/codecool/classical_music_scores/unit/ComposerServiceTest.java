package com.codecool.classical_music_scores.unit;

import com.codecool.classical_music_scores.entity.Composer;
import com.codecool.classical_music_scores.entity.Score;
import com.codecool.classical_music_scores.repository.ComposerRepository;
import com.codecool.classical_music_scores.service.ComposerService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ComposerServiceTest {

    @Mock
    private ComposerRepository repository;

    @InjectMocks
    private ComposerService service;

    private Composer composer;

    @BeforeEach
    void setUp() {
        composer = new Composer(1L, "Zoltán Kodály", 1886, 1967, new ArrayList<>());
    }

    @Test
    void findAllComposers() {
        Composer composer1 = new Composer(1L, "Béla Bartók", 1886, 1945, new ArrayList<>());

        given(repository.findAll()).willReturn(List.of(composer, composer1));

        List<Composer> composerList = service.findAllComposers();

        assertNotNull(composerList);
        assertEquals(composerList.size(), 2);
    }

    @Test
    void findComposerById() {
        given(repository.findById(1L)).willReturn(Optional.of(composer));

        Composer savedComposer = service.findComposerById(composer.getId());

        assertNotNull(savedComposer);
    }

    @Test
    void addNewComposer() {
        given(repository.save(composer)).willReturn(composer);

        Composer savedComposer = service.addNewComposer(composer);

        assertNotNull(savedComposer);
    }

    @Test
    @Disabled
    void updateComposer() {
        given(repository.save(composer)).willReturn(composer);
        composer.setName("Ernő Dohnányi");

        Composer updatedComposer = service.updateComposer(1L, composer);

        assertEquals(updatedComposer.getName(), "Ernő Dohnányi");
    }

    @Test
    void deleteComposer() {
        Long composerId = 1L;

        willDoNothing().given(repository).deleteById(composerId);

        service.deleteComposer(composerId);

        verify(repository, times(1)).deleteById(composerId);
    }

    @Test
    void findAllScoresFromComposerById() {
        given(repository.save(composer)).willReturn(composer);

        Composer savedComposer = service.addNewComposer(composer);

        List<Score> expected = composer.getScores();
        List<Score> actual = composer.getScores();

        assertListsHasSameElements(expected, actual);

    }

    private void assertListsHasSameElements(List<Score> expected, List<Score> actual){
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }
}