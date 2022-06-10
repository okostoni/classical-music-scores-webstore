package com.codecool.classical_music_scores.entity;

import com.codecool.classical_music_scores.entity.type.InstrumentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "composer_id")
    @JsonBackReference
    private Composer composer;
    
    @Enumerated(value = EnumType.STRING)
    private InstrumentType instrumentType;

    private Integer yearOfCreation;
    private double price;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonBackReference
    private Publisher publisher;

    private boolean isAvailableInStock;

}
