package com.codecool.classical_music_scores.entity;

import com.codecool.classical_music_scores.entity.type.InstrumentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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
    
    @Enumerated(EnumType.STRING)
    private InstrumentType instrumentType;
    
    private Year yearOfCreation;
    private int price;

    @OneToOne
    private Publisher publisher;

    private boolean isAvailableInStock;

}
