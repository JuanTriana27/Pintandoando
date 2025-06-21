package com.pinturillo.mapper;

import com.pinturillo.dto.RoundDTO;
import com.pinturillo.dto.request.CreateRoundRequest;
import com.pinturillo.dto.response.CreateRoundResponse;
import com.pinturillo.model.Round;

public class RoundMapper {

    // Metodo modelToDTO
    public static RoundDTO modelToDTO(Round round){
        return RoundDTO.builder()
                .idRound(round.getIdRound())
                .startTime(round.getStartTime())
                .endTime(round.getEndTime())
                .idRoom(round.getRoom() != null ?
                        round.getRoom().getIdRoom() : null)
                .idWord(round.getWord() != null ?
                        round.getWord().getIdWord() : null)
                .idPlayer(round.getPlayer() != null ?
                        round.getPlayer().getIdPlayer() : null)
                .build();
    }

    // Metodo Request
    public static Round CreateRequestToModel(CreateRoundRequest request){
        return Round.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    // Metodo Response
    public static CreateRoundResponse modelToCreateResponse(Round round){
        return CreateRoundResponse.builder()
                .idRound(round.getIdRound())
                .startTime(round.getStartTime())
                .endTime(round.getEndTime())
                .idRoom(round.getRoom() != null ?
                        round.getRoom().getIdRoom() : null)
                .idWord(round.getWord() != null ?
                        round.getWord().getIdWord() : null)
                .idPlayer(round.getPlayer() != null ?
                        round.getPlayer().getIdPlayer() : null)
                .build();
    }
}
