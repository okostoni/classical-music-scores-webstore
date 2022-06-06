package com.codecool.classical_music_scores.entity;

import com.codecool.classical_music_scores.entity.type.InstrumentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Year;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @OneToOne
    @JsonBackReference
    private Composer composer;

    private InstrumentType instrumentType;
    private Year yearOfCreation;
    private double price;

    @OneToOne
    private Publisher publisher;


    private boolean isAvailableInStock;

}
