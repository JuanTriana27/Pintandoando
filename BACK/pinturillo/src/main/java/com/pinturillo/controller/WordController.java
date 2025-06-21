package com.pinturillo.controller;

import com.pinturillo.dto.WordDTO;
import com.pinturillo.dto.request.CreateWordRequest;
import com.pinturillo.dto.response.ApiResponse;
import com.pinturillo.dto.response.CreateWordResponse;
import com.pinturillo.model.Word;
import com.pinturillo.service.impl.WordServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("word")
@RequiredArgsConstructor
public class WordController {

    private final WordServiceImpl wordService;

    // Obtener Todas las words
    @GetMapping("/all")
    public List<Word> getAllWords() {
        return wordService.getAllWords();
    }

    // Obtener por ID
    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<ApiResponse> getWordById(@PathVariable Integer id) {
        try {
            WordDTO wordDTO = wordService.getWordById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Word found", wordDTO));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Guardar Word
    @PostMapping("/save-new")
    public ResponseEntity<ApiResponse> saveWord(@RequestBody CreateWordRequest request) {
        try {
            CreateWordResponse createWordResponse = wordService.createWordResponse(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Word created", createWordResponse));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Actualizar Word
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateWord(@PathVariable Integer id, @RequestBody CreateWordRequest request) {
        try {
            CreateWordResponse createWordResponse = wordService.updateWord(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Word updated", createWordResponse));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Eliminar Word
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteWord(@PathVariable Integer id) {
        try {
            wordService.deleteWord(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Word deleted", null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}