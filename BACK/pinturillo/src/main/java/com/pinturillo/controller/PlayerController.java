package com.pinturillo.controller;

import com.pinturillo.dto.PlayerDTO;
import com.pinturillo.dto.request.CreatePlayerRequest;
import com.pinturillo.dto.response.ApiResponse;
import com.pinturillo.dto.response.CreatePlayerResponse;
import com.pinturillo.model.Player;
import com.pinturillo.service.impl.PlayerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerServiceImpl playerService;

    // Obtener Todo
    @GetMapping("/all")
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    // Obtener por ID
    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<ApiResponse> getPlayerById(@PathVariable Integer id){
        try{
            PlayerDTO playerDTO = playerService.getPlayerById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Player found", playerDTO));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Guardar Player
    @PostMapping("/save-new")
    public ResponseEntity<ApiResponse> savePlayer(@RequestBody CreatePlayerRequest request){
        try{
            CreatePlayerResponse createPlayerResponse = playerService.createPlayerResponse(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Player created", createPlayerResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Actualizar
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updatePlayer(@PathVariable Integer id, @RequestBody CreatePlayerRequest request){
        try{
            CreatePlayerResponse createPlayerResponse = playerService.updatePlayer(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Player updated", createPlayerResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Eliminar
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePlayer(@PathVariable Integer id){
        try{
            playerService.deletePlayer(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Player deleted", null));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
