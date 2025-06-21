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
public class RoundDTO {
    private Integer idRound;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // IDs Relación
    private Integer idRoom;
    private Integer idWord;
    private Integer idPlayer;
}