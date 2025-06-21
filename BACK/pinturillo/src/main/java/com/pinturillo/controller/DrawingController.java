package com.pinturillo.controller;

import com.pinturillo.dto.DrawingDTO;
import com.pinturillo.dto.request.CreateDrawingRequest;
import com.pinturillo.dto.response.ApiResponse;
import com.pinturillo.dto.response.CreateDrawingResponse;
import com.pinturillo.model.Drawing;
import com.pinturillo.service.impl.DrawingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("drawing")
@RequiredArgsConstructor
public class DrawingController {

    private final DrawingServiceImpl drawingService;

    // Obtener Todo
    @GetMapping("/all")
    public List<Drawing>getAllDrawings(){
        return drawingService.getAllDrawings();
    }

    // Obtener por ID
    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<ApiResponse> getDrawingById(@PathVariable Integer id){
        try{
            DrawingDTO drawingDTO = drawingService.getDrawingById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Drawing found", drawingDTO));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Guardar Draw
    @PostMapping("/save-new")
    public ResponseEntity<ApiResponse> saveDrawing(@RequestBody CreateDrawingRequest request){
        try{
            CreateDrawingResponse createDrawingResponse = drawingService.createDrawingResponse(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Drawing created", createDrawingResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Actualizar
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateDrawing(@PathVariable Integer id, @RequestBody CreateDrawingRequest request){
        try{
            CreateDrawingResponse createDrawingResponse = drawingService.updateDrawing(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Drawing updated", createDrawingResponse));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // Eliminar
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteDrawing(@PathVariable Integer id){
        try {
            drawingService.deleteDrawing(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Drawing deleted", null));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}