package com.example.websocket.chatting.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.exception.ChatRoomException;
import com.example.websocket.chatroom.exception.ErrorStatus;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatting.domain.ChatId;
import com.example.websocket.chatting.domain.Chat;
import com.example.websocket.chatting.repository.ChatRepository;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ChatSaveServiceTest {

    @Autowired
    private ChatRepository chattingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    public void 채팅_저장_성공() {
        //given
        Long userId = 1L;
        Long chatRoomId = 1L;
        String comment = "테스트";

        // when
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NO_ACTIVE_USER_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NOT_FOUND_CHATROOM));

        Long maxChatId = chattingRepository.findMaxChatIdByChatRoomId(chatRoom.getId());

        Chat chatting = Chat.builder()
                .id(new ChatId(maxChatId + 1, chatRoom.getId()))
                .chatroom(chatRoom)
                .user(user)
                .comment(comment)
//                .createDate(LocalDateTime.now())
                .build();
        Chat save = chattingRepository.save(chatting);

        Chat findChatting = chattingRepository.findById(save.getId()).get();

        // then
        assertThat(findChatting).isNotNull();
        assertThat(findChatting.getId().getChatId()).isEqualTo(1);
        assertThat(findChatting.getComment()).isEqualTo(comment);


    }

}