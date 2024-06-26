package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.domain.QChatRoom;
import com.example.websocket.chatroom.domain.QInviteChat;
import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatting.domain.QChat;
import com.example.websocket.friend.domain.QFriend;
import com.example.websocket.friend.dto.response.UserInfo;
import com.example.websocket.user.domain.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.StringHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ChatRoomQueryRepositoryImpl implements ChatRoomQueryRepository {

    private final static int PAGE_SIZE = 10;
    private final QInviteChat inviteChat;
    private final QInviteChat inviteChat2;
    private final QChatRoom chatRoom;
    private final QUser user;
    private final QChat chat;
    private final JPAQueryFactory query;

    public ChatRoomQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
        this.inviteChat = QInviteChat.inviteChat;
        this.inviteChat2 = QInviteChat.inviteChat;
        this.chatRoom = QChatRoom.chatRoom;
        this.user = QUser.user;
        this.chat = QChat.chat;
    }

    // 채팅방에 없는 멤버를 조회
    @Override
    public Slice<UserInfo> findMemberNotInChatRoom(Long chatRoomId, String nickname, Integer cursorId) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        QUser user = QUser.user;
        QFriend friend = QFriend.friend1;
        QUser user2 = new QUser("user2");

        // email이 비어있는 상태로 전송되었다면 빈 리스트를 반환해준다.
        if(StringHelper.isEmpty(nickname)) {
            return new SliceImpl<>(Collections.emptyList(), pageable, false);
        }

        List<UserInfo> userInfos = query.select(Projections.constructor(UserInfo.class,
                        user2.id,
                        user2.nickname,
                        user2.profileImg,
                        user2.statusMessage))
                .from(user)
                .join(friend).on(user.id.eq(friend.friendId.userId))
                .join(user2).on(user2.id.eq(friend.friendId.friendId))
                .where(user2.id.notIn(getFriendForChatRoom(chatRoomId, inviteChat))
                        .and(user2.nickname.like("%"+nickname+"%")))
                .offset(cursorId)
                .limit(pageable.getPageSize() + 1)
                .fetch();

        // 다음 페이지가 있는지 확인
        Boolean hasNext = hasNextList(userInfos, pageable);

        return new SliceImpl<>(userInfos, pageable, hasNext);
    }

    @Override
    public List<ChatRoomListDto> findChatRoomParticipate(Long userId, String title) {
        QChat chat2 = new QChat("chat2");
        List<ChatRoomListDto> results = query.select(Projections.constructor(ChatRoomListDto.class,
                        chatRoom.id,
                        chatRoom.title,
                        chatRoom.profileImg,
                        subQueryForPersonCount(), //personCount
                        subQueryForLastCommnetDate(), // lastCommentDate
                        subQueryForLastComment(chat2) // lastComment
                ))
                .from(chatRoom)
                .join(inviteChat).on(chatRoom.id.eq(inviteChat.inviteChatId.chatRoomId))
                .join(user).on(inviteChat.inviteChatId.userId.eq(user.id))
                .where(user.id.eq(userId),
                        titleLike(title))
                .groupBy(chatRoom.id)
                .fetch();


        return results;
    }

    private BooleanExpression titleLike(String title) {
        return StringUtils.hasText(title) ? chatRoom.title.like("%" + title + "%") : null;
    }

    private JPQLQuery<String> subQueryForLastComment(QChat chat2) {
        return JPAExpressions.select(chat.comment).from(chat).where(chat.chatroom.id.eq(chatRoom.id).and(chat.createDate.eq(JPAExpressions.select(chat2.createDate.max()).from(chat2).where(chat2.chatroom.id.eq(chatRoom.id)))));
    }

    private JPQLQuery<String> subQueryForLastCommnetDate() {
        return JPAExpressions.select(chat.createDate.max()).from(chat).where(chat.chatroom.id.eq(chatRoom.id));
    }

    private JPQLQuery<Long> subQueryForPersonCount() {
        return JPAExpressions.select(inviteChat2.count()).from(inviteChat2).where(inviteChat2.inviteChatId.chatRoomId.eq(chatRoom.id));
    }

    private static JPQLQuery<Long> getFriendForChatRoom(Long chatRoomId, QInviteChat inviteChat) {
        return JPAExpressions.select(inviteChat.inviteChatId.userId)
                .from(inviteChat)
                .where(inviteChat.inviteChatId.chatRoomId.eq(chatRoomId));
    }

    private JPQLQuery<Long> calculatePersonCount(NumberPath<Long> chatRoomId) {
        return JPAExpressions.select(inviteChat2.count())
                .from(inviteChat2)
                .where(inviteChat2.inviteChatId.chatRoomId.eq(chatRoomId));
    }

    private <T> Boolean hasNextList(List<T> list, Pageable pageable) {
        boolean hasNext = list.size() > pageable.getPageSize();
        if(hasNext) {
            list.remove(list.size() - 1); // 다음 페이지가 있으면 리스트에서 마지막 요소 제거
        }
        return hasNext;
    }
}
