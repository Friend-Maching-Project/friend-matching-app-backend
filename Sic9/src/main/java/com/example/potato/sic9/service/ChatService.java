package com.example.potato.sic9.service;

import com.example.potato.sic9.dto.chat.ChatDto;
import com.example.potato.sic9.entity.Chat;
import com.example.potato.sic9.repository.ChatRepository;
import com.example.potato.sic9.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void saveChat(ChatDto dto) {
        chatRepository.save(Chat.of(dto, chatRoomRepository.findChatRoomByChatRoomUUID(dto.getChatRoomUUID())));
    }
}
