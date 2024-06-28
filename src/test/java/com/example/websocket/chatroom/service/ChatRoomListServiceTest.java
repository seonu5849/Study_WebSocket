package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.domain.InviteChat;
import com.example.websocket.chatroom.domain.InviteChatId;
import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatroom.repository.InviteRepository;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ChatRoomLstServiceTest {

    @InjectMocks
    private ChatRoomListService chatRoomListService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InviteRepository inviteRepository;


    @Test
    @DisplayName("채팅방 리스트 정상 테스트 케이스")
    public void chatRoomListViewTest() {
        Long userId = 1L;

        Object[] result1 = {1L, "Room1", "profile1.jpg", 5L, null, "Hello"};
        Object[] result2 = {2L, "Room2", "profile2.jpg", 3L, null, "Hi"};
        List<Object[]> results = List.of(result1, result2);

        when(chatRoomRepository.findByUserIdWithChatRoom(userId)).thenReturn(results);

        // when
        List<ChatRoomListDto> response = chatRoomListService.chatRoomListView(userId);

        // then
        log.info("response: {}", response);
        assertThat(response).isNotNull();
        assertThat(response.get(0).getChatRoomId()).isEqualTo(1L);
        assertThat(response.get(0).getTitle()).isEqualTo("Room1");
        assertThat(response.get(1).getChatRoomId()).isEqualTo(2L);
    }
}