package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.domain.QChatRoom2;
import com.example.websocket.chatroom.domain.QInviteChat;
import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatting.domain.QChatting;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ChatRoomQueryRepositoryImplTest {

    @Autowired
    private JPAQueryFactory query;
    @Autowired
    private EntityManager entityManager;

    private QChatRoom2 chatRoom = QChatRoom2.chatRoom2;
    private QInviteChat inviteChat = QInviteChat.inviteChat;
    private QInviteChat inviteChat2 = QInviteChat.inviteChat;
    private QChatting chatting = QChatting.chatting;

    @Test
    public void 채팅방_리스트_출력_성공_테스트() {
        Long userId = 1L;
        List<ChatRoomListDto> result = query.select(Projections.constructor(ChatRoomListDto.class,
                        chatRoom.id.as("chatRoomId"),
                        chatRoom.title,
                        chatRoom.profileImg,
                        inviteChat2.count().as("personCount"),
                        chatting.createDate.max().as("lastCommentDate"),
                        chatting.comment.as("lastComment")))
                .from(chatRoom)
                .join(inviteChat).on(chatRoom.id.eq(inviteChat.inviteChatId.chatRoomId))
                .leftJoin(chatting).on(chatRoom.id.eq(chatting.chatroom2.id))
                .where(inviteChat.inviteChatId.userId.eq(userId))
                .groupBy(chatRoom.id, chatRoom.title, chatRoom.profileImg, chatting.createDate, chatting.comment)
                .orderBy(chatting.createDate.desc())
                .fetch();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

}