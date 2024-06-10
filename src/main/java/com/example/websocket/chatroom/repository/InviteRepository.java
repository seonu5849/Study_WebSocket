package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.domain.InviteChat;
import com.example.websocket.chatroom.domain.InviteChatId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<InviteChat, InviteChatId> {
}
