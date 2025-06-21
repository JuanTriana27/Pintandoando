package com.pinturillo.service;

import com.pinturillo.dto.RoundDTO;
import com.pinturillo.dto.request.CreateRoundRequest;
import com.pinturillo.dto.response.CreateRoundResponse;
import com.pinturillo.model.Round;

import java.util.List;

public interface RoundService {

    // Obtener Todo
    List<Round>getAllRounds();

    // Consular por ID
    RoundDTO getRoundById(Integer idRound);

    // Crear Round
    CreateRoundResponse createRoundResponse(CreateRoundRequest request) throws Exception;

    // Actualizar Round
    CreateRoundResponse updateRound(Integer idRound, CreateRoundRequest request) throws Exception;

    // Eliminar Round
    void deleteRound(Integer idRound) throws Exception;

    // Terminar Round (setear endTime)
    CreateRoundResponse endRound(Integer idRound) throws Exception;
}