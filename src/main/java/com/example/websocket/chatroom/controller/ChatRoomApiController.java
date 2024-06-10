package com.example.websocket.chatroom.controller;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class WebSocketController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatRoom> createChatRoom(String name) {
        log.info("Create ChatRoom");
        ChatRoom chatRoom = chatService.createChatRoom(name);
        return ResponseEntity.ok(chatRoom);
    }

}
