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
public class CreatePlayerRequest {
    private String playerName;
    private Integer score;
    private Boolean isOwner;

    // IDs Relación
    private Integer idRoom;
}