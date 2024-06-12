package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.dto.response.ChatRoomListDto;

import java.util.List;
import java.util.Optional;

public interface ChatRoomQueryRepository {

    List<ChatRoomListDto> findChatRoomsByUserId(Long userId);

}
