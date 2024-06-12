package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.domain.QChatRoom2;
import com.example.websocket.chatroom.domain.QInviteChat;
import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatting.domain.QChatting;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class ChatRoomQueryRepositoryImpl implements ChatRoomQueryRepository {

    private final QChatRoom2 chatRoom;
    private final QInviteChat inviteChat;
    private final QInviteChat inviteChat2;
    private final QChatting chatting;
    private final JPAQueryFactory query;

    public ChatRoomQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
        this.chatRoom = QChatRoom2.chatRoom2;
        this.inviteChat = QInviteChat.inviteChat;
        this.inviteChat2 = QInviteChat.inviteChat;
        this.chatting = QChatting.chatting;
    }

    @Override
    public List<ChatRoomListDto> findChatRoomsByUserId(Long userId) {

        // 채팅방 : 방이름, 방아이디, 방사진, 명수,    채팅 : 마지막 채팅, 마지막 채팅 날짜
        return query.select(Projections.constructor(ChatRoomListDto.class,
                        chatRoom.id,
                        chatRoom.title,
                        chatRoom.profileImg,
                        calculatePersonCount(),
                        chatting.createDate.max(),
                        chatting.comment))
                .from(chatRoom)
                .join(inviteChat).on(chatRoom.id.eq(inviteChat.inviteChatId.chatRoomId))
                .leftJoin(chatting).on(chatRoom.id.eq(chatting.chatroom2.id))
                .where(inviteChat.inviteChatId.userId.eq(userId))
                .groupBy(chatRoom.id, chatRoom.title, chatRoom.profileImg, chatting.createDate, chatting.comment)
                .orderBy(chatting.createDate.desc())
                .fetch();
    }

    private JPQLQuery<Long> calculatePersonCount() {
        return JPAExpressions.select(inviteChat2.count())
                .from(inviteChat2)
                .where(inviteChat2.inviteChatId.chatRoomId.eq(chatRoom.id));
    }
}
