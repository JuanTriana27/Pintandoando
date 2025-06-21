package com.pinturillo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guesses")
public class Guess {

    // Mapear Datos de la db
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_guesses")
    private Integer idGuess;

    @Column(name = "guess", nullable = false)
    private String guess;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Llaves de relación
    @ManyToOne
    @JoinColumn(name = "id_round", referencedColumnName = "id_round", nullable = false)
    private Round round;

    @ManyToOne
    @JoinColumn(name = "id_player", referencedColumnName = "id_player", nullable = false)
    private Player player;
}