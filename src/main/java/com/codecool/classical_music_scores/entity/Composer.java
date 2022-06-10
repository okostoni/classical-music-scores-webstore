package com.codecool.classical_music_scores.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Composer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer yearOfBirth;
    private Integer yearOfDeath;

    @OneToMany(mappedBy = "composer")
    @JsonManagedReference
    private List<Score> scores;

}
