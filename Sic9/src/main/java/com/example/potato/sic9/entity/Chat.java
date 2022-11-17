package com.example.potato.sic9.entity;

import com.example.potato.sic9.dto.chat.ChatDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "chat")
@ToString
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @Column(name = "chat_sender_nickname", nullable = false)
    private String senderNickname;

    @Column(name = "chat_receiver_nickname", nullable = false)
    private String receiverNickname;

    @Column(name = "chat_message", nullable = false)
    private String message;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    public Chat(String senderNickname, String receiverNickname, String message, ChatRoom chatRoom) {
        this.senderNickname = senderNickname;
        this.receiverNickname = receiverNickname;
        this.message = message;
        this.chatRoom = chatRoom;
    }

    public static Chat of(ChatDto dto, ChatRoom chatRoom) {
        return new Chat(dto.getSenderNickname(), dto.getReceiverNickname(), dto.getMessage(), chatRoom);
    }
}
