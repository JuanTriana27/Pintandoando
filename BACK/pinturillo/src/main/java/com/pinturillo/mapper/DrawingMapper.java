package com.pinturillo.mapper;

import com.pinturillo.dto.DrawingDTO;
import com.pinturillo.dto.request.CreateDrawingRequest;
import com.pinturillo.dto.response.CreateDrawingResponse;
import com.pinturillo.model.Drawing;

public class DrawingMapper {

    // Metodo modelToDTO
    public static DrawingDTO modelToDTO(Drawing drawing){
        return DrawingDTO.builder()
                .idDrawing(drawing.getIdDrawing())
                .data(drawing.getData())
                .createdAt(drawing.getCreatedAt())
                .idRound(drawing.getRound() != null ?
                        drawing.getRound().getIdRound() : null)
                .build();
    }

    // Metodo Request
    public static Drawing CreateRequestToModel(CreateDrawingRequest request){
        return Drawing.builder()
                .data(request.getData())
                .build();
    }

    // Metodo Response
    public static CreateDrawingResponse modelToCreateResponse(Drawing drawing){
        return CreateDrawingResponse.builder()
                .idDrawing(drawing.getIdDrawing())
                .data(drawing.getData())
                .createdAt(drawing.getCreatedAt())
                .idRound(drawing.getRound() != null ?
                        drawing.getRound().getIdRound() : null)
                .build();
    }
}