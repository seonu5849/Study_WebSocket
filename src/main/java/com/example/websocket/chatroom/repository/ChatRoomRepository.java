package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.domain.Chatroom2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<Chatroom2, Long> {
}
