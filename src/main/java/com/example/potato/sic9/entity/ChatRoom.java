package com.example.potato.sic9.entity;

import com.example.potato.sic9.dto.chat.ChatRoomRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Builder
@ToString
@Getter
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(name = "chat_room_UUID", nullable = false)
    private String chatRoomUUID;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Exclude
    @Setter
    private List<Chat> chats = new ArrayList<>();

    @Builder
    public ChatRoom(Long id, String chatRoomUUID, List<Chat> chats) {
        this.id = id;
        this.chatRoomUUID = chatRoomUUID;
        this.chats = chats;
    }

    public ChatRoom(String writerId, String receiverId) {
        this.chatRoomUUID = createUUID(writerId, receiverId);
    }

    public static ChatRoom of(ChatRoomRequestDto dto) {
        return new ChatRoom(dto.getSenderId(), dto.getReceiverId());
    }

    public static String createUUID(String writerId, String receiverId) {
        String uuid = UUID.randomUUID().toString();
        if (Integer.parseInt(writerId) > Integer.parseInt(receiverId)) {
            uuid = receiverId + uuid.substring(receiverId.length(),
                    uuid.length() - writerId.length()) + writerId;
        } else {
            uuid = writerId + uuid.substring(writerId.length(),
                    uuid.length() - receiverId.length()) + receiverId;
        }
        return uuid;
    }
}
