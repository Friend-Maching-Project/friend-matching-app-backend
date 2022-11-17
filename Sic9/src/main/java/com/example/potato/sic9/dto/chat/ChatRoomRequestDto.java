package com.example.potato.sic9.dto.chat;

import lombok.Data;

@Data
public class ChatRoomRequestDto {
    private String senderId;
    private String receiverId;
}
