package com.codecool.classical_music_scores.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Composer {

    private long id;
    private String name;
    private Year yearOfBirth;
    private Year yearOfDeath;
    private List<Score> listOfWorks;

}
