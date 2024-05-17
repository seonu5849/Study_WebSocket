package com.example.websocket.service;

import com.example.websocket.domain.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ObjectMapper mapper;
    private Map<String, ChatRoom> chatRooms = new LinkedHashMap<>();

    // 채팅방 생성
    public ChatRoom createChatRoom(String name) {
        String roomId = UUID.randomUUID().toString();// 고유 번호 생성

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .name(name)
                .build();

        chatRooms.put(roomId, chatRoom);
        return chatRoom;
    }

    // 채팅방 찾기
    public ChatRoom findById(String id) {
        return chatRooms.get(id);
    }

}
