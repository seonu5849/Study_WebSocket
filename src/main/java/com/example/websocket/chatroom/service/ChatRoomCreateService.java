package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.domain.ChatRoom;
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
import java.util.stream.Collectors;

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

        chatRoomCreateDto.enterToChatRoom(userId);
        ChatRoom chatRoomEntity = createChatRoomEntity(chatRoomCreateDto);
        log.info("chatRoomCreateDto : {}", chatRoomCreateDto);
        ChatRoom savedChatRoom = saveChatRoom(chatRoomEntity);
        log.info("savedChatRoom: {}", savedChatRoom);
        inviteUsersToChatRoom(savedChatRoom, chatRoomCreateDto.getUserIds());

        return savedChatRoom.getId();
    }

    private ChatRoom createChatRoomEntity(ChatRoomCreateDto chatRoomCreateDto) {
        return chatRoomRepository.save(chatRoomCreateDto.toChatRoomEntity());
    }

    private ChatRoom saveChatRoom(ChatRoom chatRoomEntity) {
        log.info("chatRoomEntity: {}", chatRoomEntity);
        return chatRoomRepository.save(chatRoomEntity);
    }

    private void inviteUsersToChatRoom(ChatRoom chatRoom, List<Long> userIds) {
        List<User> users = findUsersByIds(userIds);
        users.forEach(user -> {
            InviteChat inviteChat = createInviteChat(chatRoom, user);
            inviteRepository.save(inviteChat);
        });
    }

    private List<User> findUsersByIds(List<Long> userIds) {
        return userIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND)))
                .collect(Collectors.toList());
    }

    private InviteChat createInviteChat(ChatRoom chatRoom, User user) {
        InviteChatId inviteChatId = new InviteChatId(user.getId(), chatRoom.getId());
        return InviteChat.builder()
                .inviteChatId(inviteChatId)
                .user(user)
                .chatroom(chatRoom)
                .build();
    }
}
