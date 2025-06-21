package com.pinturillo.repository;

import com.pinturillo.model.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawingRepository extends JpaRepository <Drawing, Integer>{
}