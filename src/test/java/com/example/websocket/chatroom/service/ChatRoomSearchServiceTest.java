package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ChatRoomSearchServiceTest {

    @Autowired
    private ChatRoomSearchService chatRoomSearchService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("참가한 모든 채팅방 리스트 ")
    public void allChatRoomListTest() {
        // given
        User user = userRepository.findById(1L).get();
        String title = null;

        // when
        List<ChatRoomListDto> search = chatRoomSearchService.search(user, title);

        // then

        Assertions.assertThat(search.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("참가한 채팅방 중 검색한 채팅방 리스트 출력")
    public void searchTest() {
        // given
        User user = userRepository.findById(1L).get();
        String title = "t";

        // when
        List<ChatRoomListDto> search = chatRoomSearchService.search(user, title);

        // then

        Assertions.assertThat(search.size()).isEqualTo(1);

    }

}