package com.codecool.classical_music_scores.entity;

import com.codecool.classical_music_scores.entity.type.InstrumentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "composer_id")
    @JsonBackReference(value = "composer")
    @NotNull
    private Composer composer;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private InstrumentType instrumentType;

    @NotNull
    private Integer yearOfCreation;

    @NotNull
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    @JsonBackReference(value = "publisher")
    @NotNull
    private Publisher publisher;

    @NotNull
    private Boolean isAvailableInStock;

}
