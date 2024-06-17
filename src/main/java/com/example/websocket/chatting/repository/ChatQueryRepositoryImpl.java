package com.example.websocket.chatting.repository;

import com.example.websocket.chatroom.domain.QChatRoom;
import com.example.websocket.chatting.domain.QChat;
import com.example.websocket.chatting.dto.response.ChatMessageDto;
import com.example.websocket.user.domain.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatQueryRepositoryImpl implements ChatQueryRepository {

    private final JPAQueryFactory query;

    public ChatQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<ChatMessageDto> findChatRoomWithMessage(Long chatRoomId) {
        QChat chat = QChat.chat;
        QUser user = QUser.user;

        return query.select(Projections.constructor(ChatMessageDto.class,
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
    }
}
