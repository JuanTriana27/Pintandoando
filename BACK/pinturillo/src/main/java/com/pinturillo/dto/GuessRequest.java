package com.pinturillo.dto;

import lombok.Data;

@Data
public class GuessRequest {
    private Integer playerId;
    private String guess;
}