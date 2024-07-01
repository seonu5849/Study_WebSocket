package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatroom.repository.ChatRoomQueryRepository;
import com.example.websocket.chatroom.repository.ChatRoomQueryRepositoryImpl;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomListService {

    private final ChatRoomQueryRepository chatRoomQueryRepository;

    @Transactional
    public List<ChatRoomListDto> chatRoomListView(Long userId) {
        List<ChatRoomListDto> chatRoomListDtoList = chatRoomQueryRepository.findChatRoomParticipate(userId, null).stream()
                .sorted(Comparator.comparing(ChatRoomListDto::getLastCommentDate).reversed())
                .toList();

        for(ChatRoomListDto chatRoom : chatRoomListDtoList) {
            if(chatRoom.getLastCommentDate() != null) {
                chatRoom.formatLastCommentDate(chatRoom.getLastCommentDate());
            }
        }

        return chatRoomListDtoList;
    }

}
