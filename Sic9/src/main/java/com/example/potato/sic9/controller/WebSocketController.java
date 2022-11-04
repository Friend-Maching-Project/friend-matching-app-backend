package com.example.potato.sic9.controller;

import com.example.potato.sic9.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(ChatDto chatDto, SimpMessageHeaderAccessor accessor) {
        /*
        '/sub/chat/{channelId} 채널을 구독 중인 클라이언트에게 메시지를 전송
        SimpMessagingTemplate는 특정 브로커로 메시지를 전달
         */
        System.out.println("/sub/channel " + chatDto);
        simpMessagingTemplate.convertAndSend("/sub/channel/" + chatDto.getChannelId(), chatDto);
    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
        String sessionId = Objects.requireNonNull(event.getMessage().getHeaders().get("simpSessionId")).toString();
        System.out.println(sessionId);
    }
}
