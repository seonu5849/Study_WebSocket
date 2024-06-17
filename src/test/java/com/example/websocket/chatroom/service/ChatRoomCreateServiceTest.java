package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.dto.request.ChatRoomCreateDto;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ChatRoomCreateServiceTest {

    @Autowired
    private ChatRoomCreateService chatRoomCreateService;

    @Test
    public void 채팅방_저장_테스트() {
        // given
        Long userId = 1L;
        List<Long> userIds = new ArrayList<>(Arrays.asList(2L, 52L));
        ChatRoomCreateDto dto = new ChatRoomCreateDto("테스트방", userIds);

        // when
        Long chatRoom = chatRoomCreateService.createChatRoom(userId, dto);

        // then
        assertThat(chatRoom).isNotNull();
    }

}