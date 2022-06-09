package com.codecool.classical_music_scores.entity;

import com.codecool.classical_music_scores.entity.type.InstrumentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.Year;

@Entity
@Data
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    @JsonBackReference
    private Composer composer;
    
    @Enumerated(value = EnumType.STRING)
    private InstrumentType instrumentType;
    private Year yearOfCreation;
    private double price;

    @ManyToOne
    @JsonBackReference
    private Publisher publisher;

    private boolean isAvailableInStock;

}
