package com.example.websocket.chatting.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.exception.ChatRoomException;
import com.example.websocket.chatroom.exception.ErrorStatus;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatting.domain.Chat;
import com.example.websocket.chatting.domain.ChatId;
import com.example.websocket.chatting.dto.request.ChatMessageRequest;
import com.example.websocket.chatting.dto.response.ChatMessageResponse;
import com.example.websocket.chatting.repository.ChatRepository;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatSaveService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transient
    public ChatMessageResponse saveChatting(ChatMessageRequest request) {
        log.debug("saveChatting: {}", request);
        ChatRoom chatRoom = getChatRoom(request);

        Long maxChatId = chatRepository.findMaxChatIdByChatRoomId(chatRoom.getId());

        Chat chat = createChatEntity(request, maxChatId, chatRoom);
        log.info("chat: {}", chat);
        Chat saved = chatRepository.save(chat);
        log.info("saved: {}", saved);
        return ChatMessageResponse.of(saved);
    }

    private Chat createChatEntity(ChatMessageRequest request, Long maxChatId, ChatRoom chatRoom) {
        return Chat.builder()
                .id(new ChatId(maxChatId + 1, chatRoom.getId()))
                .chatroom(chatRoom)
                .user(getUser(request))
                .comment(request.getMessage())
                .createDate(request.getSendTime())
                .messageType(request.getMessageType())
                .build();
    }

    private User getUser(ChatMessageRequest chatMessageDto) {
        return userRepository.findById(chatMessageDto.getUserInfo().getId())
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NO_ACTIVE_USER_FOUND));
    }

    private ChatRoom getChatRoom(ChatMessageRequest chatMessageDto) {
        return chatRoomRepository.findById(chatMessageDto.getChatRoomId())
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NOT_FOUND_CHATROOM));
    }

}
