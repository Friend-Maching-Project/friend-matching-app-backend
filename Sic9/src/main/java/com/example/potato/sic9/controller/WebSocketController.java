package com.example.potato.sic9.controller;

import com.example.potato.sic9.dto.chat.ChatDto;
import com.example.potato.sic9.service.ChatService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate template;   // 특정 Broker로 메시지를 전달
    private final ChatService chatService;

    @MessageMapping("/private-message")
    public void handle(@Payload ChatDto payload, Principal principal) {
        payload.setSenderNickname(principal.getName());
        template.convertAndSendToUser(payload.getSenderNickname(), "/queue/something", payload);
        template.convertAndSendToUser(payload.getReceiverNickname(), "/queue/something", payload);
        chatService.saveChat(payload);
    }
}
