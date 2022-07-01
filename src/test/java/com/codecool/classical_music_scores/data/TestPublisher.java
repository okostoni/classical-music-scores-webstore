package com.codecool.classical_music_scores.data;

import com.codecool.classical_music_scores.testmodels.Publisher;

import java.util.ArrayList;

public interface TestPublisher {

    Publisher BUDAPEST = new Publisher(null, "Editio Musica Budapest", new ArrayList<>());
    Publisher BERLIN = new Publisher(null, "Berliner Barenreiter Edition", new ArrayList<>());
    Publisher ROME = new Publisher(null, "Editio Rome Italia", new ArrayList<>());
    Publisher MADRID = new Publisher(null, "Spanish Music Press", new ArrayList<>());
}
