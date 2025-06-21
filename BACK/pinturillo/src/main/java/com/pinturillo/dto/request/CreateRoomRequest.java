package com.pinturillo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {
    private Integer idRoom;
    private String code;
    private String roomName;
    private Integer maxPlayers;
    private String status;
    private LocalDate createdAt;
}