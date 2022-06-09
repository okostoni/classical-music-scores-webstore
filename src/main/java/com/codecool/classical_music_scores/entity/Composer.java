package com.codecool.classical_music_scores.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Year;
import java.util.List;

@Entity
@Data
public class Composer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Year yearOfBirth;
    private Year yearOfDeath;

    @OneToMany(mappedBy = "composer")
    private List<Score> listOfWorks;

}
