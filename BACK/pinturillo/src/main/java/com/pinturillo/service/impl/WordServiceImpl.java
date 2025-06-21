package com.pinturillo.service.impl;

import com.pinturillo.dto.WordDTO;
import com.pinturillo.dto.request.CreateWordRequest;
import com.pinturillo.dto.response.CreateWordResponse;
import com.pinturillo.mapper.WordMapper;
import com.pinturillo.model.Word;
import com.pinturillo.repository.WordRepository;
import com.pinturillo.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    // Obtener Todas las words
    @Override
    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    // Consultar por ID
    @Override
    public WordDTO getWordById(Integer idWord) {
        Word word = wordRepository.findById(idWord)
                .orElseThrow(() -> new RuntimeException("Word not found"));

        return WordMapper.modelToDTO(word);
    }

    // Crear Word
    @Override
    public CreateWordResponse createWordResponse(CreateWordRequest createWordRequest) throws Exception {

        if (createWordRequest == null) {
            throw new Exception("Word is null");
        }

        if (createWordRequest.getWord() == null || createWordRequest.getWord().trim().isEmpty()) {
            throw new Exception("Word text is null or empty");
        }

        Word word = WordMapper.CreateRequestToModel(createWordRequest);

        word = wordRepository.save(word);

        return WordMapper.modelToCreateResponse(word);
    }

    // Actualizar Word
    @Override
    public CreateWordResponse updateWord(Integer idWord, CreateWordRequest createWordRequest) throws Exception {

        if (createWordRequest == null) {
            throw new Exception("Word is null");
        }

        if (createWordRequest.getWord() == null || createWordRequest.getWord().trim().isEmpty()) {
            throw new Exception("Word text is null or empty");
        }

        Word word = wordRepository.findById(idWord)
                .orElseThrow(() -> new RuntimeException("Word not found"));

        word.setWord(createWordRequest.getWord());

        word = wordRepository.save(word);

        return WordMapper.modelToCreateResponse(word);
    }

    // Eliminar Word
    @Override
    public void deleteWord(Integer idWord) throws Exception {

        if (!wordRepository.existsById(idWord)) {
            throw new Exception("Word not found");
        }

        wordRepository.deleteById(idWord);
    }
}