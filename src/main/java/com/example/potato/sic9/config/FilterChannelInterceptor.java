package com.example.potato.sic9.config;

import com.example.potato.sic9.security.jwt.JwtTokenProvider;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class FilterChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert headerAccessor != null;
        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {

            String token = Objects.requireNonNull(headerAccessor.getNativeHeader("Authorization")).get(0)
                    .replace("Bearer ", "");
            try {
                Authentication user = jwtTokenProvider.getAuthentication(token);
                headerAccessor.setUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}
