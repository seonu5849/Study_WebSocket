package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatroom.repository.ChatRoomQueryRepository;
import com.example.websocket.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomSearchService {

    private final ChatRoomQueryRepository chatRoomQueryRepository;

    public List<ChatRoomListDto> search(User user, String title) {

        List<ChatRoomListDto> chatRoomListList = chatRoomQueryRepository.findChatRoomParticipate(user.getId(), title).stream()
                .sorted(Comparator.comparing(ChatRoomListDto::getLastCommentDate).reversed())
                .toList();
        log.info("chatRoomListList: {}", chatRoomListList);

        return chatRoomListList;
    }

}
