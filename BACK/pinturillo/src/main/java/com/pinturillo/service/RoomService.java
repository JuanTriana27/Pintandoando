package com.pinturillo.service;

import com.pinturillo.dto.RoomDTO;
import com.pinturillo.dto.request.CreateRoomRequest;
import com.pinturillo.dto.response.CreateRoomResponse;
import com.pinturillo.model.Room;
import java.util.List;

public interface RoomService {

    // Obtener Todas las Salas
    List<Room> getAllRomms();

    // Consultar por ID
    RoomDTO getRoomById(Integer idRoom);

    // Crear Sala
    CreateRoomResponse createRoomResponse(CreateRoomRequest request) throws Exception;

    // Actualizar Sala
    CreateRoomResponse updateRoom(Integer idRoom, CreateRoomRequest request) throws Exception;

    // Eliminar Sala
    void deleteRoom(Integer idRoom) throws Exception;
}