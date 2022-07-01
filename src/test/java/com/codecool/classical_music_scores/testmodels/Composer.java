package com.codecool.classical_music_scores.testmodels;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Composer {

    private Long id;
    private String name;
    private Integer yearOfBirth;
    private Integer yearOfDeath;
    private List<Score> scores;

}
