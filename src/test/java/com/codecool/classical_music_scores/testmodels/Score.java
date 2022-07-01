package com.codecool.classical_music_scores.testmodels;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Score {

    private Long id;
    private String title;
    private Composer composer;
    private InstrumentType instrumentType;
    private Integer yearOfCreation;
    private Double price;
    private Publisher publisher;
    private Boolean isAvailableInStock;

}
