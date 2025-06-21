package com.pinturillo.service;

import com.pinturillo.dto.PlayerDTO;
import com.pinturillo.dto.request.CreatePlayerRequest;
import com.pinturillo.dto.response.CreatePlayerResponse;
import com.pinturillo.model.Player;

import java.util.List;

public interface PlayerService {

    // Obtener Todos los Player
    List<Player>getAllPlayers();

    // Consultar por ID
    PlayerDTO getPlayerById(Integer idPlayer);

    // Crear Player
    CreatePlayerResponse createPlayerResponse(CreatePlayerRequest request) throws Exception;

    // Actualizar Player
    CreatePlayerResponse updatePlayer(Integer idPlayer, CreatePlayerRequest request) throws Exception;

    // Eliminar Player
    void deletePlayer(Integer idPlayer) throws Exception;
}