package com.pinturillo.mapper;

import com.pinturillo.dto.GuessDTO;
import com.pinturillo.dto.request.CreateGuessRequest;
import com.pinturillo.dto.response.CreateGuessResponse;
import com.pinturillo.model.Guess;

public class GuessMapper {

    // Metodo modelToDTO
    public static GuessDTO modelToDTO (Guess guess){
        return GuessDTO.builder()
                .idGuess(guess.getIdGuess())
                .guess(guess.getGuess())
                .isCorrect(guess.getIsCorrect())
                .createdAt(guess.getCreatedAt())
                .idRound(guess.getRound() != null ?
                        guess.getRound().getIdRound() : null)
                .idPlayer(guess.getPlayer() != null ?
                        guess.getPlayer().getIdPlayer() : null)
                .build();
    }

    // Metodo Request
    public static Guess CreateRequestToModel(CreateGuessRequest request){
        return Guess.builder()
                .guess(request.getGuess())
                .isCorrect(request.getIsCorrect())
                .build();
    }

    // Metodo Response
    public static CreateGuessResponse modelToCreateResponse(Guess guess){
        return CreateGuessResponse.builder()
                .idGuess(guess.getIdGuess())
                .guess(guess.getGuess())
                .isCorrect(guess.getIsCorrect())
                .createdAt(guess.getCreatedAt())
                .idRound(guess.getRound() != null ?
                        guess.getRound().getIdRound() : null)
                .idPlayer(guess.getPlayer() != null ?
                        guess.getPlayer().getIdPlayer() : null)
                .build();
    }
}
