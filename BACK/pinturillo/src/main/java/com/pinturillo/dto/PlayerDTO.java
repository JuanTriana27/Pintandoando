package com.pinturillo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Integer idPlayer;
    private String playerName;
    private Integer score;
    private Boolean isOwner;
    private LocalDateTime createdAt;

    // IDs Relación
    private Integer idRoom;
}