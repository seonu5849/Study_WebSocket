package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.friend.dto.response.UserInfo;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface ChatRoomQueryRepository {

    Slice<UserInfo> findMemberNotInChatRoom(Long chatRoomId, String nickname, Integer cursorId);

    List<ChatRoomListDto> findChatRoomParticipate(Long userId, String title);

}
