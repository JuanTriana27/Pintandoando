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
@Table(name = "drawings")
public class Drawing {

    // Mapear Datos de la db
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_drawing")
    private Integer idDrawing;

    @Column(name = "data", nullable = false)
    private String data;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Llaves de Relación
    @ManyToOne
    @JoinColumn(name = "id_round", referencedColumnName = "id_round", nullable = false)
    private Round round;
}
