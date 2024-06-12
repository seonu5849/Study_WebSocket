package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatroom.repository.ChatRoomQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomListService {

    private final ChatRoomQueryRepository chatRoomQueryRepository;

    @Transactional
    public List<ChatRoomListDto> chatRoomListView(Long userId) {
        return chatRoomQueryRepository.findChatRoomsByUserId(userId);
    }

}
