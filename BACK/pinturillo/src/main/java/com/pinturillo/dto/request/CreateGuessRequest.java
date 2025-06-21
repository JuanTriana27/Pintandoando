package com.pinturillo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGuessRequest {
    private String guess;
    private Boolean isCorrect;

    // IDs Relación
    private Integer idRound;
    private Integer idPlayer;
}