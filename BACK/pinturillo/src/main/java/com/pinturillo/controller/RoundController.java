package com.pinturillo.controller;

import com.pinturillo.dto.RoundDTO;
import com.pinturillo.dto.request.CreateRoundRequest;
import com.pinturillo.dto.response.ApiResponse;
import com.pinturillo.dto.response.CreateRoundResponse;
import com.pinturillo.model.Round;
import com.pinturillo.service.impl.RoundServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("round")
@RequiredArgsConstructor
public class RoundController {

    private final RoundServiceImpl roundService;

    // Obtener Todo
    @GetMapping("/all")
    public List<Round> getAllRounds(){
        return roundService.getAllRounds();
    }

    // Obtener por ID
    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<ApiResponse> getRoundById(@PathVariable Integer id){
        try{
            RoundDTO roundDTO = roundService.getRoundById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Round found", roundDTO));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Guardar Round
    @PostMapping("/save-new")
    public ResponseEntity<ApiResponse> saveRound(@RequestBody CreateRoundRequest request){
        try{
            CreateRoundResponse createRoundResponse = roundService.createRoundResponse(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Round created", createRoundResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Actualizar Round
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateRound(@PathVariable Integer id, @RequestBody CreateRoundRequest request){
        try{
            CreateRoundResponse createRoundResponse = roundService.updateRound(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Round updated", createRoundResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Eliminar Round
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRound(@PathVariable Integer id){
        try{
            roundService.deleteRound(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Round deleted", null));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Terminar Round (setear endTime)
    @PutMapping("/end/{id}")
    public ResponseEntity<ApiResponse> endRound(@PathVariable Integer id){
        try{
            CreateRoundResponse roundResponse = roundService.endRound(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Round ended", roundResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}