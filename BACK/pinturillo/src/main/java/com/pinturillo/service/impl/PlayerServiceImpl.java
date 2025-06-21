package com.pinturillo.service.impl;

import com.pinturillo.dto.PlayerDTO;
import com.pinturillo.dto.request.CreatePlayerRequest;
import com.pinturillo.dto.response.CreatePlayerResponse;
import com.pinturillo.mapper.PlayerMapper;
import com.pinturillo.model.Player;
import com.pinturillo.model.Room;
import com.pinturillo.repository.PlayerRepository;
import com.pinturillo.repository.RoomRepository;
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

    // Obtener Todo
    @Override
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    // Obtener por ID
    @Override
    public PlayerDTO getPlayerById(Integer idPlayer){

        // Consultar en db por su id
        Player player = playerRepository.findById(idPlayer)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        // Mapear hacia DTO el resultado que trae el modelo
        PlayerDTO playerDTO = PlayerMapper.modelToDTO(player);

        // Retornamos objeto mapeado a DTO
        return playerDTO;
    }

    // Crear en back
    @Override
    public CreatePlayerResponse createPlayerResponse(CreatePlayerRequest createPlayerRequest) throws Exception{

        // Validar que player no sea nulo
        if(createPlayerRequest == null){
            throw new Exception("Player is null");
        }

        // Validar player name
        if(createPlayerRequest.getPlayerName() == null){
            throw new Exception("Player name is null");
        }

        // Validar score
        if(createPlayerRequest.getScore() == null){
            throw new Exception("Score is null");
        }

        // Validar owner
        if(createPlayerRequest.getIsOwner() == null){
            throw new Exception("Is owner is null");
        }

        // Validar Relaciones
        if(createPlayerRequest.getIdRoom() == null || createPlayerRequest.getIdRoom() <= 0){
            throw new Exception("Id room is invalid");
        }

        // Cargar ID
        Room room = roomRepository.findById(createPlayerRequest.getIdRoom())
                .orElseThrow(() -> new Exception("Room with id: " + createPlayerRequest.getIdRoom() + " not found"));

        // Mapear el request a Modelo
        Player player = PlayerMapper.CreateRequestToModel(createPlayerRequest);

        // Seteo de Creacion, room
        player.setCreatedAt(LocalDateTime.now());
        player.setRoom(room);

        // Persistir el modelo en db
        player = playerRepository.save(player);

        // Convertir a Response para Retornar
        CreatePlayerResponse playerResponse = PlayerMapper.modelToCreateResponse(player);

        // Retornamos el response persistido como solicita el metodo
        return playerResponse;
    }

    // Actualizar
    @Override
    public CreatePlayerResponse updatePlayer(Integer idPlayer, CreatePlayerRequest createPlayerRequest) throws Exception{

        // Verificamos que exista
        Player player = playerRepository.findById(idPlayer)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        // Validar player name
        if(createPlayerRequest.getPlayerName() == null){
            throw new Exception("Player name is null");
        }

        // Validar score
        if(createPlayerRequest.getScore() == null){
            throw new Exception("Score is null");
        }

        // Validar owner
        if(createPlayerRequest.getIsOwner() == null){
            throw new Exception("Is owner is null");
        }

        // Validar IDs de ralacion
        if(createPlayerRequest.getIdRoom() == null || createPlayerRequest.getIdRoom() <= 0){
            throw new Exception("Id room is invalid");
        }

        // Cargamos relacion
        Room room = roomRepository.findById(createPlayerRequest.getIdRoom())
                .orElseThrow(() -> new Exception("Room with id: " + createPlayerRequest.getIdRoom() + " not found"));

        // Actualizamos datos
        player.setPlayerName(createPlayerRequest.getPlayerName());
        player.setScore(createPlayerRequest.getScore());
        player.setIsOwner(createPlayerRequest.getIsOwner());
        player.setRoom(room);

        // Guardar Player
        player = playerRepository.save(player);

        // Mapear
        CreatePlayerResponse playerResponse = PlayerMapper.modelToCreateResponse(player);

        // Retornar
        return playerResponse;
    }

    // Eliminar
    @Override
    public void deletePlayer(Integer idPlayer) throws Exception{

        // Verificamos que exista
        if(!playerRepository.existsById(idPlayer)){
            throw new Exception("Player not found");
        }

        // Eliminamos
        playerRepository.deleteById(idPlayer);
    }
}