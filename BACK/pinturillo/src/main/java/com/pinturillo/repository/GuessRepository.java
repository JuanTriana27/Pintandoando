package com.pinturillo.repository;

import com.pinturillo.model.Guess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuessRepository extends JpaRepository <Guess, Integer>{
}