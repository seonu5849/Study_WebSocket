package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.dto.request.ChatRoomCreateDto;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatroom.repository.InviteRepository;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//@DataJpaTest
@ExtendWith(MockitoExtension.class)
class ChatRoomCreateServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private InviteRepository inviteRepository;

    @InjectMocks
    private ChatRoomCreateService chatRoomCreateService;

    @Test
    public void 채팅방_저장_테스트() {
        // given
        User user1 = new User(1L, "123", "123", "123", null,null,null);
        User user2 = new User(2L, "1234", "1234", "1234", null,null,null);

        given(userRepository.findById(user1.getId()))
                .willReturn(Optional.of(user1));
        given(userRepository.findById(user2.getId()))
                .willReturn(Optional.of(user2));

        // Mock 객체에서 ChatRoomRepository.save 메서드의 동작 정의
        doAnswer(invocation -> {
            ChatRoom chatRoom = invocation.getArgument(0);
            ReflectionTestUtils.setField(chatRoom, "id", 1L);
            return chatRoom;
        }).when(chatRoomRepository).save(any(ChatRoom.class));

        List<Long> userIds = new ArrayList<>(Arrays.asList(user2.getId()));
        ChatRoomCreateDto dto = new ChatRoomCreateDto("테스트방", userIds);

        // when
        Long chatRoomId = chatRoomCreateService.createChatRoom(user1.getId(), dto);

        // then
        assertThat(chatRoomId).isNotNull();
        assertThat(chatRoomId).isEqualTo(1L);
    }
}