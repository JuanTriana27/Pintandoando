package com.pinturillo.repository;

import com.pinturillo.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository <Round, Integer>{
}