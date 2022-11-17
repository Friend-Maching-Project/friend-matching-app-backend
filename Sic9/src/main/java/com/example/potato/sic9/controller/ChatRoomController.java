package com.example.potato.sic9.controller;

import com.example.potato.sic9.dto.chat.ChatRoomRequestDto;
import com.example.potato.sic9.dto.chat.ChatRoomResponseDto;
import com.example.potato.sic9.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/room")
    public ResponseEntity<ChatRoomResponseDto> enterRoom(@RequestBody ChatRoomRequestDto dto) {
        if (!chatRoomService.existsUUID(dto)) {
            return ResponseEntity.ok(chatRoomService.saveChatRoom(dto));
        } else {
            return ResponseEntity.ok(chatRoomService.findByUuid(dto));
        }
    }
}
