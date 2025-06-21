package com.pinturillo.mapper;

import com.pinturillo.dto.RoomDTO;
import com.pinturillo.dto.request.CreateRoomRequest;
import com.pinturillo.dto.response.CreateRoomResponse;
import com.pinturillo.model.Room;

public class RoomMapper {

    // Metodo modelToDTO
    public static RoomDTO modelToDTO(Room room){
        return RoomDTO.builder()
                .idRoom(room.getIdRoom())
                .code(room.getCode())
                .roomName(room.getRoomName())
                .maxPlayers(room.getMaxPlayers())
                .status(room.getStatus())
                .createdAt(room.getCreatedAt())
                .build();
    }

    // Metodo Request
    public static Room CreateRequestToModel(CreateRoomRequest request){
        return Room.builder()
                .code(request.getCode())
                .roomName(request.getRoomName())
                .maxPlayers(request.getMaxPlayers())
                .status(request.getStatus())
                .build();
    }

    // Metodo Response
    public static CreateRoomResponse modelToCreateResponse(Room room){
        return CreateRoomResponse.builder()
                .idRoom(room.getIdRoom())
                .code(room.getCode())
                .roomName(room.getRoomName())
                .maxPlayers(room.getMaxPlayers())
                .status(room.getStatus())
                .createdAt(room.getCreatedAt())
                .build();
    }
}