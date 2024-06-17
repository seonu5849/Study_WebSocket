package com.example.websocket.chatting.repository;

import com.example.websocket.chatroom.domain.QChatRoom;
import com.example.websocket.chatting.domain.QChat;
import com.example.websocket.chatting.dto.response.ChatMessageDto;
import com.example.websocket.user.domain.QUser;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class ChatQueryRepositoryImplTest {

    @Autowired
    private JPAQueryFactory query;

    @Test
    public void 채팅방_번호로_채팅_메시지_찾기() {
        // given
        final Long chatRoomId = 2L;
        final Long userId = 1L;
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QChat chat = QChat.chat;
        QUser user = QUser.user;

        // when
        List<ChatMessageDto> chatMessageDtoList = query.select(Projections.constructor(ChatMessageDto.class,
                        user.id,
                        user.nickname,
                        user.profileImg,
                        chat.comment,
                        chat.createDate))
                .from(chat)
                .join(user).on(chat.user.id.eq(user.id))
                .where(chat.chatroom.id.eq(chatRoomId))
                .orderBy(chat.createDate.asc())
                .fetch();

        for(ChatMessageDto chatMessage : chatMessageDtoList) {
            chatMessage.updateMessageOwner(chatMessage.getUserId());
        }

        String comment = chatMessageDtoList.get(1).getComment();
        if(comment.indexOf('\n') != -1) {
            log.info("한줄 띔");
        }

        // then
        log.info("chatMessageDtoList: {}", comment);
        log.info("comment size: {}", comment.length());
        assertThat(chatMessageDtoList).isNotNull();
//        assertThat(chatMessageDtoList.size()).isEqualTo(4);
    }

}