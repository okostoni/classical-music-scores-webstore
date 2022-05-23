package com.codecool.classical_music_scores.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {

    private int id;
    private String name;
    private List<Score> scoreList;

}
