package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.friend.dto.response.UserInfo;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ChatRoomQueryRepository {

    List<ChatRoomListDto> findChatRoomsByUserId(Long userId);

    Slice<UserInfo> findMemberNotInChatRoom(Long chatRoomId, String nickname, Integer cursorId);

}
