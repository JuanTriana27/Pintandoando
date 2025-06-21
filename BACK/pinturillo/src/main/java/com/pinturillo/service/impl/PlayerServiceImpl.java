package com.pinturillo.service.impl;

import com.pinturillo.dto.PlayerDTO;
import com.pinturillo.dto.request.CreatePlayerRequest;
import com.pinturillo.dto.response.CreatePlayerResponse;
import com.pinturillo.mapper.PlayerMapper;
import com.pinturillo.model.Player;
import com.pinturillo.model.Room;
import com.pinturillo.repository.PlayerRepository;
import com.pinturillo.repository.RoomRepository;
import com.pinturillo.service.GameService;
import com.pinturillo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final RoomRepository roomRepository;
    private final GameService gameService;

    // Obtener todos los jugadores
    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // Obtener jugador por ID
    @Override
    public PlayerDTO getPlayerById(Integer idPlayer) {
        Player player = playerRepository.findById(idPlayer)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        return PlayerMapper.modelToDTO(player);
    }

    // Crear jugador + disparar lógica de juego
    @Override
    public CreatePlayerResponse createPlayerResponse(CreatePlayerRequest req) throws Exception {
        // Validaciones
        if (req == null) {
            throw new Exception("Player is null");
        }
        if (req.getPlayerName() == null || req.getPlayerName().isBlank()) {
            throw new Exception("Player name is null or empty");
        }
        if (req.getScore() == null) {
            throw new Exception("Score is null");
        }
        if (req.getIsOwner() == null) {
            throw new Exception("Is owner is null");
        }
        if (req.getIdRoom() == null || req.getIdRoom() <= 0) {
            throw new Exception("Id room is invalid");
        }

        // Cargar sala
        Room room = roomRepository.findById(req.getIdRoom())
                .orElseThrow(() -> new Exception("Room with id: " + req.getIdRoom() + " not found"));

        // Mapear y persistir
        Player player = PlayerMapper.CreateRequestToModel(req);
        player.setCreatedAt(LocalDateTime.now());
        player.setRoom(room);
        player = playerRepository.save(player);

        // Disparar asignación de rol si la sala se llenó
        gameService.onPlayerJoined(room.getIdRoom());

        // Mapear a respuesta
        return PlayerMapper.modelToCreateResponse(player);
    }

    // Actualizar jugador
    @Override
    public CreatePlayerResponse updatePlayer(Integer idPlayer, CreatePlayerRequest req) throws Exception {
        Player player = playerRepository.findById(idPlayer)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        if (req.getPlayerName() == null || req.getPlayerName().isBlank()) {
            throw new Exception("Player name is null or empty");
        }
        if (req.getScore() == null) {
            throw new Exception("Score is null");
        }
        if (req.getIsOwner() == null) {
            throw new Exception("Is owner is null");
        }
        if (req.getIdRoom() == null || req.getIdRoom() <= 0) {
            throw new Exception("Id room is invalid");
        }

        Room room = roomRepository.findById(req.getIdRoom())
                .orElseThrow(() -> new Exception("Room with id: " + req.getIdRoom() + " not found"));

        player.setPlayerName(req.getPlayerName());
        player.setScore(req.getScore());
        player.setIsOwner(req.getIsOwner());
        player.setRoom(room);

        player = playerRepository.save(player);
        return PlayerMapper.modelToCreateResponse(player);
    }

    // Eliminar jugador
    @Override
    public void deletePlayer(Integer idPlayer) throws Exception {
        if (!playerRepository.existsById(idPlayer)) {
            throw new Exception("Player not found");
        }
        playerRepository.deleteById(idPlayer);
    }
}