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
@Table(name = "rounds")
public class Round {

    // Mapear Datos de la db
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_round")
    private Integer idRound;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    // Llaves de Relación
    @ManyToOne
    @JoinColumn(name = "id_room", referencedColumnName = "id_room",nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "id_word", referencedColumnName = "id_word",nullable = false)
    private Word word;

    @ManyToOne
    @JoinColumn(name = "id_player", referencedColumnName = "id_player",nullable = false)
    private Player player;
}