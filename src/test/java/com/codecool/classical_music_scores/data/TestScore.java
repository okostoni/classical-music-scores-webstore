package com.codecool.classical_music_scores.data;

import com.codecool.classical_music_scores.testmodels.InstrumentType;
import com.codecool.classical_music_scores.testmodels.Score;

import static com.codecool.classical_music_scores.data.TestComposer.*;
import static com.codecool.classical_music_scores.data.TestPublisher.*;

public interface TestScore {

    Score SONATA = new Score(null, "Piano Sonata no.13 in C minor", BEETHOVEN, InstrumentType.PIANO,
            1798, 7500.00, BUDAPEST, true);
    Score PRELUDE_AND_FUGUE = new Score(null, "Prelude and fugue in C Major", BACH, InstrumentType.ORGAN,
            1723, 5500.00, BERLIN, true);
    Score CONCERTO = new Score(null, "Concerto in A minor", VIVALDI, InstrumentType.VIOLIN,
            1689, 7000.00, ROME, true);
    Score SUITE = new Score(null, "Suite in B minor", BACH, InstrumentType.FLUTE,
            1733, 8000.00, BERLIN, false);
    Score ASTURIAS = new Score(null, "Asturias", ALBENIZ, InstrumentType.GUITAR,
            1895, 4000.00, MADRID, true);
}
