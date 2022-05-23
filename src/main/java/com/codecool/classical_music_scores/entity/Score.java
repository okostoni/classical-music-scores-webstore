package com.codecool.classical_music_scores.entity;

import com.codecool.classical_music_scores.entity.dto.InstrumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {

    private long id;
    private String title;
    private Composer composer;
    private InstrumentType instrumentType;
    private Year yearOfCreation;
    private double price;
    private Publisher publisher;
    private boolean isAvailableInStock;

}
