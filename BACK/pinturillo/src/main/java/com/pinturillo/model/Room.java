package com.pinturillo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    // Mapear Datos de la db
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_room")
    private Integer idRoom;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "max_players", nullable = false)
    private Integer maxPlayers;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
}
