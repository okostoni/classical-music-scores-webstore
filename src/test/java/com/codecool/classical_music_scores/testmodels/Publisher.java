package com.codecool.classical_music_scores.testmodels;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Publisher {

    private Long id;
    private String name;
    private List<Score> scores;

}
