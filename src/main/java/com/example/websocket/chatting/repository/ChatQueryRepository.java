package com.example.websocket.chatting.repository;

import com.example.websocket.chatting.dto.response.ChatMessageDto;
import com.example.websocket.chatting.dto.response.ChatRoomDto;

import java.util.List;

public interface ChatQueryRepository {

    List<ChatMessageDto> findChatRoomWithMessage(Long chatRoomId);

}
