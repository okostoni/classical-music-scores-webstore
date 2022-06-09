package com.codecool.classical_music_scores.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @JsonManagedReference
    private List<Score> scores;

    @OneToMany
    @JsonManagedReference
    private List<Composer> composers;

}
