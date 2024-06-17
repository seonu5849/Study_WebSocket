package com.example.websocket.chatroom.repository;


import com.example.websocket.chatroom.domain.QChatRoom;
import com.example.websocket.chatroom.domain.QInviteChat;
import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatting.domain.QChat;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class ChatRoomQueryRepositoryImplTest {

    @Autowired
    private JPAQueryFactory query;
    @Autowired
    private EntityManager entityManager;

    private QChatRoom chatRoom = QChatRoom.chatRoom;
    private QInviteChat inviteChat = QInviteChat.inviteChat;
    private QInviteChat inviteChat2 = QInviteChat.inviteChat;
    private QChat chat = QChat.chat;

    @Disabled
    @Test
    public void 채팅방_리스트_출력_성공_테스트() {
        Long userId = 1L;
        List<ChatRoomListDto> result = query.select(Projections.constructor(ChatRoomListDto.class,
                        chatRoom.id.as("chatRoomId"),
                        chatRoom.title,
                        chatRoom.profileImg,
                        inviteChat2.count().as("personCount"),
                        chat.createDate.max().as("lastCommentDate"),
                        chat.comment.as("lastComment")))
                .from(chatRoom)
                .join(inviteChat).on(chatRoom.id.eq(inviteChat.inviteChatId.chatRoomId))
                .leftJoin(chat).on(chatRoom.id.eq(chat.chatroom.id))
                .where(inviteChat.inviteChatId.userId.eq(userId))
                .groupBy(chatRoom.id, chatRoom.title, chatRoom.profileImg, chat.createDate, chat.comment)
                .orderBy(chat.createDate.desc())
                .fetch();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void 채팅방마다_메시지_1개씩만_출력되도록() {
        // given
        Long chatRoomId = 2L;
        QChat chat2 = new QChat("chat2");

        // when
        List<String> fetch = query.select(chat2.comment)
                .from(chat2)
                .where(chat2.chatroom.id.eq(chatRoomId))
                .orderBy(chat2.createDate.desc())
                .limit(1)
                .fetch();
        log.info("message: {}", fetch);
    }

    @Test
    public void 채팅방_목록_출력() {
        // given
        Long userId = 1L;

        List<ChatRoomListDto> results = query.select(Projections.constructor(ChatRoomListDto.class,
                        chatRoom.id,
                        chatRoom.title,
                        chatRoom.profileImg,
                        JPAExpressions.select(inviteChat2.count())
                                .from(inviteChat2)
                                .where(inviteChat2.inviteChatId.chatRoomId.eq(chatRoom.id)),
                        chat.createDate.max(),
                        chat.comment))
                .from(chatRoom)
                .join(inviteChat).on(chatRoom.id.eq(inviteChat.inviteChatId.chatRoomId))
                .leftJoin(chat).on(chatRoom.id.eq(chat.chatroom.id))
                .where(inviteChat.inviteChatId.userId.eq(userId))
                .groupBy(chatRoom.id, chat.comment)
                .orderBy(chat.createDate.max().desc())
                .fetch();

        for(ChatRoomListDto result : results) {
            log.info("result: {}", result);
        }

        // 같은 chatRoomId 내에서 lastCommentDate가 가장 큰 항목 선택
        List<ChatRoomListDto> collect = results.stream()
                // chatRoomId로 그룹화
                .collect(Collectors.toMap(ChatRoomListDto::getChatRoomId,
                        Function.identity(),
                        // chatRoomId가 같을 경우 큰 LastCommentDate를 출력
                        BinaryOperator.maxBy(Comparator.comparing(ChatRoomListDto::getLastCommentDate))
                ))
                // 맵의 값들만 추출하여 리스트로 변환
                .values().stream()
                .toList();

        for(ChatRoomListDto c : collect) {
            log.info("filter12: {}", c);
        }

    }

}