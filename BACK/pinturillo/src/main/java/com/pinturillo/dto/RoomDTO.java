package com.pinturillo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Integer idRoom;
    private String code;
    private String roomName;
    private Integer maxPlayers;
    private String status;
    private LocalDateTime createdAt;
}