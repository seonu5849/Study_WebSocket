package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.domain.ChatRoom2;
import com.example.websocket.chatroom.domain.InviteChat;
import com.example.websocket.chatroom.domain.InviteChatId;
import com.example.websocket.chatroom.dto.request.ChatRoomCreateDto;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatroom.repository.InviteRepository;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.exception.ErrorStatus;
import com.example.websocket.user.exception.UserException;
import com.example.websocket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomCreateService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final InviteRepository inviteRepository;

    @Transactional
    public Long createChatRoom(Long userId, ChatRoomCreateDto chatRoomCreateDto) {
        log.debug("createChatRoom({}, {}) invoked.", userId, chatRoomCreateDto);

        // 본인도 채팅방이 생성될 때 가입이 되어야 하기 때문에 추가
        chatRoomCreateDto.getUserIds().add(userId);
        List<User> users = findById(chatRoomCreateDto);

        ChatRoom2 savedChatRoom = chatRoomRepository.save(chatRoomCreateDto.toChatRoomEntity());

        users.forEach(user -> {
            InviteChatId inviteChatId = new InviteChatId(user.getId(), savedChatRoom.getId());
            InviteChat inviteChat = InviteChat.builder()
                    .inviteChatId(inviteChatId)
                    .user(user)
                    .chatroom(savedChatRoom)
                    .build();
            inviteRepository.save(inviteChat);
        });
        return savedChatRoom.getId();
    }

    // 유저가 존재하는지 체크하는 로직
    private List<User> findById(ChatRoomCreateDto chatRoomCreateDto) {
        return chatRoomCreateDto.getUserIds().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND)))
                .toList();
    }
}
