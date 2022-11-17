package com.example.potato.sic9.service;

import com.example.potato.sic9.dto.chat.ChatRoomRequestDto;
import com.example.potato.sic9.dto.chat.ChatRoomResponseDto;
import com.example.potato.sic9.entity.ChatRoom;
import com.example.potato.sic9.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomResponseDto saveChatRoom(ChatRoomRequestDto dto) {
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.of(dto));
        return ChatRoomResponseDto.from(chatRoom);
    }

    public ChatRoomResponseDto findByUuid(ChatRoomRequestDto dto) {
        ChatRoom chatRoom;
        if (Integer.parseInt(dto.getSenderId()) > Integer.parseInt(dto.getReceiverId())) {
            chatRoom = chatRoomRepository.findChatRoomByChatRoomUUIDStartingWithAndChatRoomUUIDEndingWith(
                    dto.getReceiverId(),
                    dto.getSenderId());
        } else {
            chatRoom = chatRoomRepository.findChatRoomByChatRoomUUIDStartingWithAndChatRoomUUIDEndingWith(
                    dto.getSenderId(),
                    dto.getReceiverId());
        }
        return ChatRoomResponseDto.from(chatRoom);
    }

    public boolean existsUUID(ChatRoomRequestDto dto) {
        if (Integer.parseInt(dto.getSenderId()) > Integer.parseInt(dto.getReceiverId())) {
            return chatRoomRepository.existsChatRoomByChatRoomUUIDStartingWithAndChatRoomUUIDEndingWith(
                    dto.getReceiverId(),
                    dto.getSenderId());
        } else {
            return chatRoomRepository.existsChatRoomByChatRoomUUIDStartingWithAndChatRoomUUIDEndingWith(
                    dto.getSenderId(),
                    dto.getReceiverId());
        }
    }
}
