package com.example.websocket.domain;

import com.example.websocket.dto.ChatMessageDto;
import com.example.websocket.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ChatRoom {

    private String roomId; // 채팅방 고유번호
    private String name; // 채팅방 이름
    private Set<WebSocketSession> sessions = new HashSet<>(); // 채팅방에 참가 중인 유저들의 세션 아이디

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

}
