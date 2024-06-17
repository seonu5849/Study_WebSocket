package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.dto.response.ChatRoomListDto;

import java.util.List;

public interface ChatRoomQueryRepository {

    List<ChatRoomListDto> findChatRoomsByUserId(Long userId);

}
