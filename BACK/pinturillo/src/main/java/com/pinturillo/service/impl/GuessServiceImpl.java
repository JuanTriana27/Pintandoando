package com.pinturillo.service.impl;

import com.pinturillo.dto.GuessDTO;
import com.pinturillo.dto.request.CreateGuessRequest;
import com.pinturillo.dto.response.CreateGuessResponse;
import com.pinturillo.mapper.GuessMapper;
import com.pinturillo.model.Guess;
import com.pinturillo.model.Player;
import com.pinturillo.model.Round;
import com.pinturillo.repository.GuessRepository;
import com.pinturillo.repository.PlayerRepository;
import com.pinturillo.repository.RoundRepository;
import com.pinturillo.service.GuessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuessServiceImpl implements GuessService {

    private final GuessRepository guessRepository;
    private final RoundRepository roundRepository;
    private final PlayerRepository playerRepository;

    // Obtener Todo
    @Override
    public List<Guess>getAllGuesses(){
        return guessRepository.findAll();
    }

    // Obtener por ID
    @Override
    public GuessDTO getGuessById(Integer idGuess){

        // Consultar en db por su id
        Guess guess = guessRepository.findById(idGuess)
                .orElseThrow(() -> new RuntimeException("Guess not found"));

        // Mapear hacia DTO el resultado que trae el modelo
        GuessDTO guessDTO = GuessMapper.modelToDTO(guess);

        // Retornamos objeto mapeado a DTO
        return guessDTO;
    }

    // Crear en back
    @Override
    public CreateGuessResponse createGuessResponse(CreateGuessRequest createGuessRequest) throws Exception{

        // Validar que no sea nulo
        if(createGuessRequest == null){
            throw new Exception("Guess is null");
        }

        // Validar Guess
        if(createGuessRequest.getGuess() == null){
            throw new Exception("Guess is null");
        }

        // Validar si es correcto
        if(createGuessRequest.getIsCorrect() == null){
            throw new Exception("Is correct is null");
        }

        // Validar Relaciones
        if(createGuessRequest.getIdRound() == null || createGuessRequest.getIdRound() <= 0){
            throw new Exception("Id round is invalid");
        }

        if(createGuessRequest.getIdPlayer() == null || createGuessRequest.getIdPlayer() <= 0){
            throw new Exception("Id player is invalid");
        }

        // Cargar ID
        Round round = roundRepository.findById(createGuessRequest.getIdRound())
                .orElseThrow(() -> new Exception("Round with id: " + createGuessRequest.getIdRound() + " not found"));

        Player player = playerRepository.findById(createGuessRequest.getIdPlayer())
                .orElseThrow(() -> new Exception("Player with id: " + createGuessRequest.getIdPlayer() + " not found"));

        // Mapeaer el request a Modelo
        Guess guess = GuessMapper.CreateRequestToModel(createGuessRequest);

        // Seteo de Creación, round, player
        guess.setRound(round);
        guess.setPlayer(player);
        guess.setCreatedAt(LocalDateTime.now());

        // Persistiri el modelo en db
        guess = guessRepository.save(guess);

        // Convertir a Response para retornar
        CreateGuessResponse guessResponse = GuessMapper.modelToCreateResponse(guess);

        // Retornamos el response persistido como solicita el metodo
        return guessResponse;
    }

    // Actualizar
    @Override
    public CreateGuessResponse updateGuess(Integer idGuess, CreateGuessRequest createGuessRequest) throws Exception{

        // Verificamos que exista
        Guess guess = guessRepository.findById(idGuess)
                .orElseThrow(() -> new RuntimeException("Guess not found"));

        // Validar guess
        if(createGuessRequest.getGuess() == null){
            throw new Exception("Guess is null");
        }

        // Validar si es correcto
        if(createGuessRequest.getIsCorrect() == null){
            throw new Exception("Is correct is null");
        }

        // Validar ids de relacion
        if(createGuessRequest.getIdRound() == null || createGuessRequest.getIdRound() <= 0){
            throw new Exception("Id round is invalid");
        }

        if(createGuessRequest.getIdPlayer() == null || createGuessRequest.getIdPlayer() <= 0){
            throw new Exception("Id player is invalid");
        }

        // Cargamos Relaciones
        Round round = roundRepository.findById(createGuessRequest.getIdRound())
                .orElseThrow(() -> new Exception("Round with id: " + createGuessRequest.getIdRound() + " not found"));

        Player player = playerRepository.findById(createGuessRequest.getIdPlayer())
                .orElseThrow(() -> new Exception("Player with id: " + createGuessRequest.getIdPlayer() + " not found"));

        // Actualizar datos
        guess.setGuess(createGuessRequest.getGuess());
        guess.setIsCorrect(createGuessRequest.getIsCorrect());
        guess.setRound(round);
        guess.setPlayer(player);

        // Guardar guess
        guess = guessRepository.save(guess);

        // Mapear
        CreateGuessResponse guessResponse = GuessMapper.modelToCreateResponse(guess);

        // Retornar
        return guessResponse;
    }

    // Eliminar
    @Override
    public void deleteGuess(Integer idGuess) throws Exception{

        // Verificamos que exista
        if(!guessRepository.existsById(idGuess)){
            throw new Exception("Guess not found");
        }

        // Eliminamos
        guessRepository.deleteById(idGuess);
    }
}