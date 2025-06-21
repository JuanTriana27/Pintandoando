package com.pinturillo.controller;

import com.pinturillo.dto.GuessDTO;
import com.pinturillo.dto.request.CreateGuessRequest;
import com.pinturillo.dto.response.ApiResponse;
import com.pinturillo.dto.response.CreateGuessResponse;
import com.pinturillo.model.Guess;
import com.pinturillo.service.impl.GuessServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("guess")
@RequiredArgsConstructor
public class GuessController {

    private final GuessServiceImpl guessService;

    // Obtener Todos
    @GetMapping("/all")
    public List<Guess>getAllGuesses(){
        return guessService.getAllGuesses();
    }

    // Obtener por ID
    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<ApiResponse> getGuessById(@PathVariable Integer id){
        try{
            GuessDTO guessDTO = guessService.getGuessById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Guess found", guessDTO));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Guardar Draw
    @PostMapping("/save-new")
    public ResponseEntity<ApiResponse> saveGuess(@RequestBody CreateGuessRequest request){
        try{
            CreateGuessResponse createGuessResponse = guessService.createGuessResponse(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Guess created", createGuessResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Actualizar
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateGuess(@PathVariable Integer id, @RequestBody CreateGuessRequest request){
        try{
            CreateGuessResponse createGuessResponse = guessService.updateGuess(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Guess updated", createGuessResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Eliminar
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteGuess(@PathVariable Integer id){
        try{
            guessService.deleteGuess(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Guess deleted", null));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}