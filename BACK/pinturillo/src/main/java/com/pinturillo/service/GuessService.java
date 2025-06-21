package com.pinturillo.service;

import com.pinturillo.dto.GuessDTO;
import com.pinturillo.dto.request.CreateGuessRequest;
import com.pinturillo.dto.response.CreateGuessResponse;
import com.pinturillo.model.Guess;

import java.util.List;

public interface GuessService {

    // Obtener Todo
    List<Guess>getAllGuesses();

    // Consultar por ID
    GuessDTO getGuessById(Integer idGuess);

    // Crear Guess
    CreateGuessResponse createGuessResponse(CreateGuessRequest request) throws Exception;

    // Actualizar Guess
    CreateGuessResponse updateGuess(Integer idGuess, CreateGuessRequest request) throws Exception;

    // Eliminar Guess
    void deleteGuess(Integer idGuess) throws Exception;
}