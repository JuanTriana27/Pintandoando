package com.pinturillo.mapper;

import com.pinturillo.dto.WordDTO;
import com.pinturillo.dto.request.CreateWordRequest;
import com.pinturillo.dto.response.CreateWordResponse;
import com.pinturillo.model.Word;

public class WordMapper {

    // Metodo modelToDTO
    public static WordDTO modelToDTO(Word word){
        return WordDTO.builder()
                .idWord(word.getIdWord())
                .word(word.getWord())
                .build();
    }

    // Metodo Request
    public static Word CreateRequestToModel(CreateWordRequest request){
        return Word.builder()
                .word(request.getWord())
                .build();
    }

    // Metodo Response
    public static CreateWordResponse modelToCreateResponse(Word word){
        return CreateWordResponse.builder()
                .idWord(word.getIdWord())
                .word(word.getWord())
                .build();
    }
}