package com.pinturillo.service.impl;

import com.pinturillo.dto.RoomDTO;
import com.pinturillo.dto.request.CreateRoomRequest;
import com.pinturillo.dto.response.CreateRoomResponse;
import com.pinturillo.mapper.RoomMapper;
import com.pinturillo.model.Room;
import com.pinturillo.repository.RoomRepository;
import com.pinturillo.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    // Obtener Todas
    @Override
    public List<Room> getAllRomms(){
        return roomRepository.findAll();
    }

    // Obtener por ID
    @Override
    public RoomDTO getRoomById(Integer idRoom){

        // Consultar en db por su ID
        Room room = roomRepository.findById(idRoom)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Mapear hacia DTO el resultado que trae el modelo
        RoomDTO roomDTO = RoomMapper.modelToDTO(room);

        // Retornar objeto mapeado a DTO
        return roomDTO;
    }

    // Crear sala en back
    @Override
    public CreateRoomResponse createRoomResponse(CreateRoomRequest createRoomRequest) throws Exception {

        // Validar que la sala no sea nula
        if(createRoomRequest == null){
            throw new Exception("Room is null");
        }

        // Validar code
        if(createRoomRequest.getCode() == null || createRoomRequest.getCode().isEmpty()){
            throw new Exception("Code is null or empty");
        }

        // Validar room name
        if(createRoomRequest.getRoomName() == null || createRoomRequest.getRoomName().isEmpty()){
            throw new Exception("Room name is null or empty");
        }

        // Validar max player
        if(createRoomRequest.getMaxPlayers() == null || createRoomRequest.getMaxPlayers() < 1){
            throw new Exception("Max players is null or less than 1");
        }

        // Valdiar Status
        if(createRoomRequest.getStatus() == null || createRoomRequest.getStatus().isEmpty()){
            throw new Exception("Status is null or empty");
        }

        // Mapear el request a Modelo
        Room room = RoomMapper.CreateRequestToModel(createRoomRequest);

        // Sete de room
        room.setCreatedAt(LocalDateTime.now());

        // Persistir el modelo en db
        room = roomRepository.save(room);

        // Convertir a Response para retornar
        CreateRoomResponse roomResponse = RoomMapper.modelToCreateResponse(room);

        // Retornamos el responsepersistido commo solicita el metodo
        return roomResponse;
    }

    // Actulizar Sala
    @Override
    public CreateRoomResponse updateRoom(Integer idRoom, CreateRoomRequest createRoomRequest) throws Exception {

        // Verificamos que exista la sala
        Room room = roomRepository.findById(idRoom)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Validar code
        if(createRoomRequest.getCode() == null || createRoomRequest.getCode().isEmpty()){
            throw new Exception("Code is null or empty");
        }

        // Validar room name
        if(createRoomRequest.getRoomName() == null || createRoomRequest.getRoomName().isEmpty()){
            throw new Exception("Room name is null or empty");
        }

        // Validar max player
        if(createRoomRequest.getMaxPlayers() == null || createRoomRequest.getMaxPlayers() < 1){
            throw new Exception("Max players is null or less than 1");
        }

        // Valdiar Status
        if(createRoomRequest.getStatus() == null || createRoomRequest.getStatus().isEmpty()){
            throw new Exception("Status is null or empty");
        }

        // Actualizar datos de la sala
        room.setCode(createRoomRequest.getCode());
        room.setRoomName(createRoomRequest.getRoomName());
        room.setMaxPlayers(createRoomRequest.getMaxPlayers());
        room.setStatus(createRoomRequest.getStatus());

        // Guardar la sala actualizada
        room = roomRepository.save(room);

        // Mapear
        CreateRoomResponse roomResponse = RoomMapper.modelToCreateResponse(room);

        // Retornar
        return roomResponse;
    }

    // Metodo para eliminar
    @Override
    public void deleteRoom(Integer idRoom) throws Exception {

        // Verificamos que exista
        if(!roomRepository.existsById(idRoom)){
            throw new Exception("Room not found");
        }

        // Eliminamos
        roomRepository.deleteById(idRoom);
    }
}