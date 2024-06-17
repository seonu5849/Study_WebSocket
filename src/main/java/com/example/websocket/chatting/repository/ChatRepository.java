package com.example.websocket.chatting.repository;

import com.example.websocket.chatting.domain.ChatId;
import com.example.websocket.chatting.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, ChatId> {

    @Query("select coalesce(max(c.id.chatId), 0) from Chat c where c.id.chatRoomId = :chatRoomId")
    Long findMaxChatIdByChatRoomId(@Param("chatRoomId") Long chatRoomId);

}
