package com.pinturillo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoundResponse {
    private Integer idRound;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // IDs Relación
    private Integer idRoom;
    private Integer idWord;
    private Integer idPlayer;
}
