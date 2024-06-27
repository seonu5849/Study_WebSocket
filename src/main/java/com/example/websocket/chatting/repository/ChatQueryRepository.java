package com.example.websocket.chatting.repository;

import com.example.websocket.chatting.dto.response.ChatMessageResponse;

import java.util.List;

public interface ChatQueryRepository {

    List<ChatMessageResponse> findChatRoomWithMessage(Long chatRoomId);

}
