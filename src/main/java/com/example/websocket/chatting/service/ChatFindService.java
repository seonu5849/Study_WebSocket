package com.example.websocket.chatting.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.exception.ChatRoomException;
import com.example.websocket.chatroom.exception.ErrorStatus;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatting.dto.response.ChatMessageDto;
import com.example.websocket.chatting.dto.response.ChatRoomDto;
import com.example.websocket.chatting.repository.ChatQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatFindService {

    private final ChatQueryRepository chatQueryRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public ChatRoomDto findChattingMessage(Long userId, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NOT_FOUND_CHATROOM));

        List<ChatMessageDto> chatMessageDtoList = chatQueryRepository.findChatRoomWithMessage(chatRoomId);

        for(ChatMessageDto chatMessage : chatMessageDtoList) {
            chatMessage.updateMessageOwner(userId);
            chatMessage.formatLastCommentDate(chatMessage.getSendTime());
        }
        log.info("chatMessageDtoList: {}", chatMessageDtoList);

        return ChatRoomDto.builder()
                .title(chatRoom.getTitle())
                .messages(chatMessageDtoList)
                .build();
    }

}
