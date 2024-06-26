package com.example.websocket.friend.repository;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.domain.InviteChat;
import com.example.websocket.chatroom.domain.QChatRoom;
import com.example.websocket.chatroom.domain.QInviteChat;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.friend.domain.QFriend;
import com.example.websocket.friend.dto.response.FriendDto;
import com.example.websocket.friend.dto.response.UserInfo;
import com.example.websocket.user.domain.QUser;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@SpringBootTest
class FriendQueryRepositoryImplTest {

    @Autowired
    private JPAQueryFactory query;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    public void 채팅에_있는_멤버들을_제외한_나머지_친구_검색() {
        // given
        final Long chatRoomId = 1L;
        final Integer cursorId = 0;

        // when
        ChatRoom chatRoom = getFindChatRoom(chatRoomId);
        Slice<UserInfo> fetch = getFetch(chatRoom, cursorId);

        // then
        for(UserInfo in : fetch) {
            log.info("inviteChat: {}", in);
        }
    }

    private Slice<UserInfo> getFetch(ChatRoom chatRoom, Integer cursorId) {
        Pageable pageable = PageRequest.of(0, 10);
        QInviteChat inviteChat = QInviteChat.inviteChat;
        QUser user = QUser.user;
        QUser user2 = new QUser("user2");
        QFriend friend = QFriend.friend1;

        List<UserInfo> fetch = query.select(Projections.constructor(UserInfo.class,
                        user2.id,
                        user2.nickname,
                        user2.profileImg,
                        user2.statusMessage))
                .from(user)
                .join(friend).on(user.id.eq(friend.friendId.userId))
                .join(user2).on(user2.id.eq(friend.friendId.friendId))
                .where(user2.id.notIn(JPAExpressions.select(inviteChat.inviteChatId.userId)
                        .from(inviteChat)
                        .where(inviteChat.inviteChatId.chatRoomId.eq(chatRoom.getId())))
                        .and(user2.nickname.like("%f%")))
                .offset(cursorId)
                .limit(pageable.getPageSize() + 1)
                .fetch();

        // 다음 페이지가 있는지 확인
        Boolean hasNext = hasNextList(fetch, pageable);
        return new SliceImpl<>(fetch, pageable, hasNext);
    }

    private ChatRoom getFindChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).get();
    }

    private <T> Boolean hasNextList(List<T> list, Pageable pageable) {
        boolean hasNext = list.size() > pageable.getPageSize();
        if(hasNext) {
            list.remove(list.size() - 1); // 다음 페이지가 있으면 리스트에서 마지막 요소 제거
        }
        return hasNext;
    }

}