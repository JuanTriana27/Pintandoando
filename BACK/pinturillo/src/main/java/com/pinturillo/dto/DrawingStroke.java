package com.pinturillo.dto;

import lombok.Data;

@Data
public class DrawingStroke {
    private double fromX;
    private double fromY;
    private double toX;
    private double toY;
    private String color;
    private int width;
}