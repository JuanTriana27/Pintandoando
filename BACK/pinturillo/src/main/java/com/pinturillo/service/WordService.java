package com.pinturillo.service;

import com.pinturillo.dto.WordDTO;
import com.pinturillo.dto.request.CreateWordRequest;
import com.pinturillo.dto.response.CreateWordResponse;
import com.pinturillo.model.Word;

import java.util.List;

public interface WordService {

    // Obtener Todas las words
    List<Word> getAllWords();

    // Consultar por ID
    WordDTO getWordById(Integer idWord);

    // Crear Word
    CreateWordResponse createWordResponse(CreateWordRequest request) throws Exception;

    // Actulizar Word
    CreateWordResponse updateWord(CreateWordRequest request) throws Exception;

    // Eliminar Word
    void deleteWord(Integer idWord) throws Exception;
}