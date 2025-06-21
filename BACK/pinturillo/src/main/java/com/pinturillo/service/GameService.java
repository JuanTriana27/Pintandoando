package com.pinturillo.service;

import com.pinturillo.model.Player;
import com.pinturillo.model.Room;
import com.pinturillo.model.Word;
import com.pinturillo.repository.PlayerRepository;
import com.pinturillo.repository.RoomRepository;
import com.pinturillo.repository.WordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameService {
    private final SimpMessagingTemplate template;
    private final RoomRepository roomRepo;
    private final PlayerRepository playerRepo;
    private final WordRepository wordRepo;
    private final Random random = new Random();

    @Transactional
    public void onPlayerJoined(Integer idRoom) {
        Room room = roomRepo.findById(idRoom).orElseThrow();
        List<Player> players = playerRepo.findByRoom(room);
        if (players.size() == room.getMaxPlayers()) {
            // elige dibujante
            Player drawer = players.get(random.nextInt(players.size()));
            // elige palabra aleatoria
            List<Word> words = wordRepo.findAll();
            String word = words.get(random.nextInt(words.size())).getWord();
            room.setStatus("IN_GAME");
            roomRepo.save(room);
            // notifica a todos
            template.convertAndSend(
                    "/topic/room/" + idRoom + "/role-assignment",
                    Map.of("drawerId", drawer.getIdPlayer(), "word", word)
            );
        }
    }
}