package com.example.potato.sic9.repository;

import com.example.potato.sic9.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findChatRoomByChatRoomUUIDStartingWithAndChatRoomUUIDEndingWith(String start, String end);

    boolean existsChatRoomByChatRoomUUIDStartingWithAndChatRoomUUIDEndingWith(String start, String end);

    ChatRoom findChatRoomByChatRoomUUID(String UUID);
}
