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
public class GuessDTO {
    private Integer idGuess;
    private String guess;
    private Boolean isCorrect;
    private LocalDateTime createdAt;

    // IDs Relación
    private Integer idRound;
    private Integer idPlayer;
}