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
@Table(name = "players")
public class Player {

    // Mapear Datos de la db
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_player")
    private Integer idPlayer;

    @Column(name = "player_name", nullable = false)
    private String playerName;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "is_owner", nullable = false)
    private Boolean isOwner;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Llaves de relación
    @ManyToOne
    @JoinColumn(name = "id_room", referencedColumnName = "id_room",nullable = false)
    private Room room;
}