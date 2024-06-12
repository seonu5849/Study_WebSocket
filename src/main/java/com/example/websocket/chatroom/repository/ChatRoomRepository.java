package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.domain.ChatRoom2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom2, Long> {
}
