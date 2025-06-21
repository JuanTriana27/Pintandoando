package com.pinturillo.service.impl;

import com.pinturillo.dto.RoundDTO;
import com.pinturillo.dto.request.CreateRoundRequest;
import com.pinturillo.dto.response.CreateRoundResponse;
import com.pinturillo.mapper.RoundMapper;
import com.pinturillo.model.Player;
import com.pinturillo.model.Room;
import com.pinturillo.model.Round;
import com.pinturillo.model.Word;
import com.pinturillo.repository.PlayerRepository;
import com.pinturillo.repository.RoomRepository;
import com.pinturillo.repository.RoundRepository;
import com.pinturillo.repository.WordRepository;
import com.pinturillo.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundServiceImpl implements RoundService {

    private final RoundRepository roundRepository;
    private final RoomRepository roomRepository;
    private final WordRepository wordRepository;
    private final PlayerRepository playerRepository;

    // Obtener Todo
    @Override
    public List<Round> getAllRounds(){
        return roundRepository.findAll();
    }

    // Obtener por ID
    @Override
    public RoundDTO getRoundById(Integer idRound){

        // Consultar en db por su id
        Round round = roundRepository.findById(idRound)
                .orElseThrow(() -> new RuntimeException("Round not found"));

        // Mapear hacia DTO el resultado que trae el modelo
        RoundDTO roundDTO = RoundMapper.modelToDTO(round);

        // Retornamos el objeto maepado a DTO
        return roundDTO;
    }

    // Crear en Back
    @Override
    public CreateRoundResponse createRoundResponse(CreateRoundRequest createRoundRequest) throws Exception {

        if(createRoundRequest == null){
            throw new Exception("Round is null");
        }

        if(createRoundRequest.getIdRoom() == null || createRoundRequest.getIdRoom() <= 0){
            throw new Exception("Id room is invalid");
        }

        if(createRoundRequest.getIdWord() == null || createRoundRequest.getIdWord() <= 0){
            throw new Exception("Id word is invalid");
        }

        if(createRoundRequest.getIdPlayer() == null || createRoundRequest.getIdPlayer() <= 0){
            throw new Exception("Id player is invalid");
        }

        Room room = roomRepository.findById(createRoundRequest.getIdRoom())
                .orElseThrow(() -> new Exception("Room with id: " + createRoundRequest.getIdRoom() + " not found"));

        Word word = wordRepository.findById(createRoundRequest.getIdWord())
                .orElseThrow(() -> new Exception("Word with id: " + createRoundRequest.getIdWord() + " not found"));

        Player player = playerRepository.findById(createRoundRequest.getIdPlayer())
                .orElseThrow(() -> new Exception("Player with id: " + createRoundRequest.getIdPlayer() + " not found"));

        Round round = RoundMapper.CreateRequestToModel(createRoundRequest);

        round.setRoom(room);
        round.setWord(word);
        round.setPlayer(player);

        // Hora de inicio
        round.setStartTime(LocalDateTime.now());

        // El endTime queda null
        round.setEndTime(null);

        round = roundRepository.save(round);

        CreateRoundResponse roundResponse = RoundMapper.modelToCreateResponse(round);

        return roundResponse;
    }

    // Actualizar Round
    @Override
    public CreateRoundResponse updateRound(Integer idRound, CreateRoundRequest createRoundRequest) throws Exception {

        // Verificamos que exista el round
        Round round = roundRepository.findById(idRound)
                .orElseThrow(() -> new Exception("Round not found"));

        // Validar relaciones
        Room room = roomRepository.findById(createRoundRequest.getIdRoom())
                .orElseThrow(() -> new Exception("Room with id: " + createRoundRequest.getIdRoom() + " not found"));

        Word word = wordRepository.findById(createRoundRequest.getIdWord())
                .orElseThrow(() -> new Exception("Word with id: " + createRoundRequest.getIdWord() + " not found"));

        Player player = playerRepository.findById(createRoundRequest.getIdPlayer())
                .orElseThrow(() -> new Exception("Player with id: " + createRoundRequest.getIdPlayer() + " not found"));

        // Actualizamos campos
        round.setRoom(room);
        round.setWord(word);
        round.setPlayer(player);

        // NO tocar startTime (dejar el que ya tenía)
        round.setStartTime(round.getStartTime());

        // Actualizar endTime (marcamos fin de ronda)
        round.setEndTime(LocalDateTime.now());

        // Guardamos
        round = roundRepository.save(round);

        // Mapeamos
        CreateRoundResponse roundResponse = RoundMapper.modelToCreateResponse(round);

        return roundResponse;
    }

    @Override
    public void deleteRound(Integer idRound) throws Exception {

        // Verificamos que exista
        if(!roundRepository.existsById(idRound)){
            throw new Exception("Round not found");
        }

        // Eliminamos
        roundRepository.deleteById(idRound);
    }

    @Override
    public CreateRoundResponse endRound(Integer idRound) throws Exception {

        // Verificamos que exista el round
        Round round = roundRepository.findById(idRound)
                .orElseThrow(() -> new Exception("Round not found"));

        // Seteamos endTime
        round.setEndTime(LocalDateTime.now());

        // Guardamos
        round = roundRepository.save(round);

        // Mapeamos
        CreateRoundResponse roundResponse = RoundMapper.modelToCreateResponse(round);

        return roundResponse;
    }
}
