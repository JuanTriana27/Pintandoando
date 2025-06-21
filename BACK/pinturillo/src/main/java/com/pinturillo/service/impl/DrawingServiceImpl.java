package com.pinturillo.service.impl;

import com.pinturillo.dto.DrawingDTO;
import com.pinturillo.dto.request.CreateDrawingRequest;
import com.pinturillo.dto.response.CreateDrawingResponse;
import com.pinturillo.mapper.DrawingMapper;
import com.pinturillo.model.Drawing;
import com.pinturillo.model.Round;
import com.pinturillo.repository.DrawingRepository;
import com.pinturillo.repository.RoundRepository;
import com.pinturillo.service.DrawingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrawingServiceImpl implements DrawingService {

    private final DrawingRepository drawingRepository;
    private final RoundRepository roundRepository;

    // Obtener Todo
    @Override
    public List<Drawing> getAllDrawings(){
        return drawingRepository.findAll();
    }

    // Obtener por ID
    @Override
    public DrawingDTO getDrawingById(Integer idDrawing){

        // Consultar en db por su id
        Drawing drawing = drawingRepository.findById(idDrawing)
                .orElseThrow(() -> new RuntimeException("Drawing not found"));

        // Mapear hacia DTO el resultado que trae el modelo
        DrawingDTO drawingDTO = DrawingMapper.modelToDTO(drawing);

        // Retornamos objeto mapeado a DTO
        return drawingDTO;
    }

    // Crear en back
    @Override
    public CreateDrawingResponse createDrawingResponse(CreateDrawingRequest createDrawingRequest) throws Exception{

        // Validar que no sea nulo
        if(createDrawingRequest == null){
            throw new Exception("Drawing is null");
        }

        // Validar data
        if(createDrawingRequest.getData() == null){
            throw new Exception("Data is null");
        }

        // Validar IDs relacion
        if(createDrawingRequest.getIdRound() == null || createDrawingRequest.getIdRound() <= 0){
            throw new Exception("Id round is invalid");
        }

        // Cargar id
        Round round = roundRepository.findById(createDrawingRequest.getIdRound())
                .orElseThrow(() -> new Exception("Round with id: " + createDrawingRequest.getIdRound() + " not found"));

        // Mapear el request a Modelo
        Drawing drawing = DrawingMapper.CreateRequestToModel(createDrawingRequest);

        // Seteo de Creación y Round
        drawing.setRound(round);
        drawing.setCreatedAt(LocalDateTime.now());

        // Persistir el modelo en db
        drawing = drawingRepository.save(drawing);

        // Convertir a Response para retornar
        CreateDrawingResponse drawingResponse = DrawingMapper.modelToCreateResponse(drawing);

        // Retornamos el response persistido como solicita el metodo
        return drawingResponse;
    }

    // Actualizar
    @Override
    public CreateDrawingResponse updateDrawing(Integer idDrawing, CreateDrawingRequest createDrawingRequest) throws Exception{

        // Verificamos que exista el draw
        Drawing drawing = drawingRepository.findById(idDrawing)
                .orElseThrow(() -> new RuntimeException("Drawing not found"));

        // Validar data
        if(createDrawingRequest.getData() == null){
            throw new Exception("Data is null");
        }

        // Cargamos la relacion
        Round round = roundRepository.findById(createDrawingRequest.getIdRound())
                .orElseThrow(() -> new Exception("Round with id: " + createDrawingRequest.getIdRound() + " not found"));

        // Actulizar datos
        drawing.setData(createDrawingRequest.getData());
        drawing.setRound(round);

        // Guardar draw
        drawing = drawingRepository.save(drawing);

        // Mapear
        CreateDrawingResponse drawingResponse = DrawingMapper.modelToCreateResponse(drawing);

        // Retornar
        return drawingResponse;
    }

    // Eliminar
    @Override
    public void deleteDrawing(Integer idDrawing) throws Exception{

        // Verificamos que exista
        if(!drawingRepository.existsById(idDrawing)){
            throw new Exception("Drawing not found");
        }

        // Eliminamos
        drawingRepository.deleteById(idDrawing);
    }
}