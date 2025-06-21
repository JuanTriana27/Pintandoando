package com.pinturillo.service;

import com.pinturillo.dto.DrawingDTO;
import com.pinturillo.dto.request.CreateDrawingRequest;
import com.pinturillo.dto.response.CreateDrawingResponse;
import com.pinturillo.model.Drawing;

import java.util.List;

public interface DrawingService {

    // Obtener Todo
    List<Drawing>getAllDrawings();

    // Consultar por ID
    DrawingDTO getDrawingById(Integer idDrawing);

    // Crear Draw
    CreateDrawingResponse createDrawingResponse(CreateDrawingRequest request) throws Exception;

    // Actualizar Draw
    CreateDrawingResponse updateDrawing(Integer idDrawing, CreateDrawingRequest request) throws Exception;

    // Eliminar Draw
    void deleteDrawing(Integer idDrawing) throws Exception;
}