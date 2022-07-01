package com.codecool.classical_music_scores.data;

import com.codecool.classical_music_scores.testmodels.Composer;

import java.util.ArrayList;

public interface TestComposer {

    Composer BACH = new Composer(null, "Johann Sebastian Bach",
            1685, 1750, new ArrayList<>());
    Composer BEETHOVEN = new Composer(null, "Ludvig van Beethoven", 1770, 1827, new ArrayList<>());
    Composer VIVALDI = new Composer(null, "Antonio Vivaldi", 1678, 1741, new ArrayList<>());
    Composer ALBENIZ = new Composer(null, "Isaac Albeniz", 1860, 1909, new ArrayList<>());
}
