package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.exception.ChatRoomException;
import com.example.websocket.chatroom.exception.ErrorStatus;
import com.example.websocket.chatroom.repository.ChatRoomQueryRepositoryImpl;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.friend.dto.response.FriendDto;
import com.example.websocket.friend.dto.response.UserInfo;
import com.example.websocket.friend.repository.FriendQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FindFriendForChatService {

    private final ChatRoomQueryRepositoryImpl chatRoomQueryRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public FriendDto findFriendForChat(Long chatRoomId, String searchNickname, Integer cursorId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NOT_FOUND_CHATROOM));
        Slice<UserInfo> userInfos = chatRoomQueryRepository.findMemberNotInChatRoom(chatRoom.getId(), searchNickname, cursorId);

        return FriendDto.builder()
                .userInfo(userInfos.toList())
                .hasNext(userInfos.hasNext())
                .build();
    }

}
