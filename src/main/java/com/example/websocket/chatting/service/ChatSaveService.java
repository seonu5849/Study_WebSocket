package com.example.websocket.chatting.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.exception.ChatRoomException;
import com.example.websocket.chatroom.exception.ErrorStatus;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatting.domain.Chat;
import com.example.websocket.chatting.domain.ChatId;
import com.example.websocket.chatting.dto.ChatMessageDto;
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

    private final ChatRepository chattingRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transient
    public void saveChatting(ChatMessageDto chatMessageDto) {
        log.debug("saveChatting: {}", chatMessageDto);
        User user = userRepository.findById(chatMessageDto.getUserInfo().getId())
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NO_ACTIVE_USER_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId())
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NOT_FOUND_CHATROOM));

        Long maxChatId = chattingRepository.findMaxChatIdByChatRoomId(chatRoom.getId());

        Chat chatting = Chat.builder()
                .id(new ChatId(maxChatId + 1, chatRoom.getId()))
                .chatroom(chatRoom)
                .user(user)
                .comment(chatMessageDto.getMessage())
                .createDate(chatMessageDto.getSendTime())
                .build();
        chattingRepository.save(chatting);
    }

}
