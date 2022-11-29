package com.example.potato.sic9.dto.chat;

import com.example.potato.sic9.entity.Chat;
import com.example.potato.sic9.entity.ChatRoom;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomResponseDto {
    private Long chatRoomId;
    private String chatRoomUUID;
    private List<Chat> chatList;

    public static ChatRoomResponseDto from(ChatRoom entity) {
        return new ChatRoomResponseDto(entity.getId(), entity.getChatRoomUUID(), entity.getChats());
    }
}
