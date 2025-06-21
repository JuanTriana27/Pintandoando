package com.pinturillo.mapper;

import com.pinturillo.dto.PlayerDTO;
import com.pinturillo.dto.request.CreatePlayerRequest;
import com.pinturillo.dto.response.CreatePlayerResponse;
import com.pinturillo.model.Player;

public class PlayerMapper {

    // Metodo modelToDTO
    public static PlayerDTO modelToDTO (Player player){
        return PlayerDTO.builder()
                .idPlayer(player.getIdPlayer())
                .playerName(player.getPlayerName())
                .score(player.getScore())
                .isOwner(player.getIsOwner())
                .createdAt(player.getCreatedAt())
                .idRoom(player.getRoom() != null ?
                        player.getRoom().getIdRoom() : null)
                .build();
    }

    // Metodo Request
    public static Player CreateRequestToModel(CreatePlayerRequest request){
        return Player.builder()
                .playerName(request.getPlayerName())
                .score(request.getScore())
                .isOwner(request.getIsOwner())
                .build();
    }

    // Metodo Response
    public static CreatePlayerResponse modelToCreateResponse(Player player){
        return CreatePlayerResponse.builder()
                .idPlayer(player.getIdPlayer())
                .playerName(player.getPlayerName())
                .score(player.getScore())
                .isOwner(player.getIsOwner())
                .createdAt(player.getCreatedAt())
                .idRoom(player.getRoom() != null ?
                        player.getRoom().getIdRoom() : null)
                .build();
    }
}