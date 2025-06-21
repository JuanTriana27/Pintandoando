package com.pinturillo.repository;

import com.pinturillo.model.Player;
import com.pinturillo.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findByRoom(Room room);
}