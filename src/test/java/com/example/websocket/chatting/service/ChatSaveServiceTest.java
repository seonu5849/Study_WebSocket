package com.example.websocket.chatting.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatting.domain.Chat;
import com.example.websocket.chatting.domain.ChatId;
import com.example.websocket.chatting.domain.MessageType;
import com.example.websocket.chatting.dto.request.ChatMessageRequest;
import com.example.websocket.chatting.dto.response.ChatMessageResponse;
import com.example.websocket.chatting.repository.ChatRepository;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

//@Transactional
// @SpringBootTest 어노테이션은 통합 테스트를 할 때 보통 사용, 이 어노테이션을 사용하면 모든 빈을 호출하기 때문에 시간이 오래 걸린다. (매개변수로 몇개 지정하면 몇개만 빈으로 가져옴)
@DataJpaTest
@ExtendWith({SpringExtension.class, MockitoExtension.class}) // 단위(유닛)테스트를 위한 어노테이션, @SpringBootTest 내에 있는 것.
@Import(ChatSaveService.class) // 테스트하고자 하는 클래스를 매개변수로 주입
class ChatSaveServiceTest {

    // @MockBean : 의존성 주입받을 객체들을 가짜 객체로 생성해서 주입
    @Mock
    private ChatRepository chatRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;

    // 테스트할 클래스에 의존성 주입
    @InjectMocks
    private ChatSaveService chatSaveService;

    /**
     * private MessageType messageType; // 메시지 타입
     *     private Long chatRoomId; // 방 번호
     *     private UserInfo userInfo; // 채팅을 보낸 사람의 정보
     *     private String message; // 메시지
     *     @Setter
     *     private String sendTime; // 보낸 시간
     */
    @Test
    @DisplayName("채팅 메시지 저장 테스트 케이스")
    public void saveChattingTest() {
        // given : 테스트할 데이터 생성
        User user = new User(1L, "123", "123", "123", null,null,null);
        ChatRoom chatRoom = new ChatRoom(1L, "testChatRoom", null, null, null);

        given(userRepository.findById(1L))
                .willReturn(Optional.of(user));
        given(chatRoomRepository.findById(1L))
                .willReturn(Optional.of(chatRoom));

        ChatMessageRequest request =
                new ChatMessageRequest(MessageType.TALK, 1L, new ChatMessageRequest.UserInfo(1L, "123", null), "테스트 메시지 입니다.", null);

        // when
        ChatMessageResponse response = chatSaveService.saveChatting(request);

        // then
        assertThat(response.getUserId()).isEqualTo(1L);
        assertThat(response.getNickname()).isEqualTo("123");
        assertThat(response.getComment()).isEqualTo("테스트 메시지 입니다.");

    }

}