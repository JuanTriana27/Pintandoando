package com.pinturillo.controller;

import com.pinturillo.dto.RoomDTO;
import com.pinturillo.dto.request.CreateRoomRequest;
import com.pinturillo.dto.response.ApiResponse;
import com.pinturillo.dto.response.CreateRoomResponse;
import com.pinturillo.model.Room;
import com.pinturillo.service.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomServiceImpl roomService;

    // Obtener Todas las Salas
    @GetMapping("/all")
    public List<Room>getAllRooms(){
        return roomService.getAllRomms();
    }

    // Obtener Sala por ID
    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<ApiResponse> getRoomById(@PathVariable Integer id){
        try{
            RoomDTO roomDTO = roomService.getRoomById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Room found", roomDTO));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Guardar Sala
    @PostMapping("/save-new")
    public ResponseEntity<ApiResponse> saveRoom(@RequestBody CreateRoomRequest request){
        try{
            CreateRoomResponse createRoomResponse = roomService.createRoomResponse(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Room created", createRoomResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Actualizar Sala
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateRoom(@PathVariable Integer id, @RequestBody CreateRoomRequest request){
        try{
            CreateRoomResponse createRoomResponse = roomService.updateRoom(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Room updated", createRoomResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Eliminar Sala
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRoom(@PathVariable Integer id){
        try{
            roomService.deleteRoom(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Room deleted", null));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
