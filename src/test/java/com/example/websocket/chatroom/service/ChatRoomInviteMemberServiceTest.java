package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.domain.InviteChat;
import com.example.websocket.chatroom.exception.ChatRoomException;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatroom.repository.InviteRepository;
import com.example.websocket.chatting.domain.Chat;
import com.example.websocket.chatting.dto.response.ChatMessageResponse;
import com.example.websocket.chatting.repository.ChatRepository;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ChatRoomInviteMemberServiceTest {

    @InjectMocks
    private ChatRoomInviteMemberService chatRoomInviteMemberService;

    @Mock
    private InviteRepository inviteRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChatRepository chatRepository;

    @Test
    @DisplayName("채팅에_초대되면_입장메시지_출력")
    public void inviteMemberForChatRoom() {
        // given
        User user = new User(1L, "123", "123", "123", null,null,null);
        ChatRoom chatRoom = new ChatRoom(1L, "testChatRoom", null, null, null);

        given(userRepository.findById(user.getId()))
                .willReturn(Optional.of(user));
        given(chatRoomRepository.findById(chatRoom.getId()))
                .willReturn(Optional.of(chatRoom));

        // 메소드 내에 jpa 엔티티 Save 메소드가 있다면 아래와 같이 작성.
        // save 메소드를 실행한다면 아래와 같이 반환하도록 처리
        given(inviteRepository.save(Mockito.any(InviteChat.class)))
                .willAnswer(AdditionalAnswers.returnsFirstArg());
        given(chatRepository.save(Mockito.any(Chat.class)))
                .willAnswer(AdditionalAnswers.returnsFirstArg());

        // when
        List<ChatMessageResponse> responses = chatRoomInviteMemberService.inviteMemberForChatRoom(chatRoom.getId(), Arrays.asList(user.getId()));

        // then
        log.info("responses: {}", responses);
        assertThat(responses).isNotNull();
        assertThat(responses.get(0).getComment()).isEqualTo(user.getNickname()+"님이 들어왔습니다.");

    }

    @Test
    @DisplayName("초대받은 채팅방이 없을 경우 예외 발생")
    public void inviteChatRoomExceptionTest() {
        // given
        User user = new User(1L, "123", "123", "123", null,null,null);
        Long chatRoomId = 1L;

        assertThrows(ChatRoomException.class, () -> {
            chatRoomInviteMemberService.inviteMemberForChatRoom(chatRoomId, Arrays.asList(user.getId()));
        });

    }

}