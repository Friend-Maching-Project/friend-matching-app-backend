package com.example.potato.sic9.dto.chat;

import lombok.Data;

@Data
public class ChatDto {
    private String message;
    private String senderNickname;
    private String receiverNickname;
    private String chatRoomUUID;
}
