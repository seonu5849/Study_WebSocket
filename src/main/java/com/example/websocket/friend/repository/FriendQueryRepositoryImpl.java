package com.example.websocket.friend.repository;

import com.example.websocket.friend.domain.QFriend;
import com.example.websocket.user.domain.QUser;
import com.example.websocket.user.domain.User;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

    private final static int PAGE_SIZE = 10;
    private final JPAQueryFactory query;
    private final QUser user;
    private final QFriend friend;

    public FriendQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
        this.friend = QFriend.friend1;
        this.user = QUser.user;
    }

    @Override
    public Slice<User> findFriendByEmail(Long userId, String email, Integer cursorId) {

        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        // email이 비어있는 상태로 전송되었다면 빈 리스트를 반환해준다.
        if(StringHelper.isEmpty(email)) {
            return new SliceImpl<>(Collections.emptyList(), pageable, false);
        }

        List<User> users = query.selectFrom(user)
                .where(user.id.ne(userId)
                        .and(user.email.like("%"+email+"%"))
                        .and(user.id.notIn(subQuery(userId))))
                .offset(cursorId)
                .limit(pageable.getPageSize() + 1) // 페이지 사이즈보다 하나 더 가져와서 다음 페이지가 있는지 확인
                .fetch();

        // 다음 페이지가 있는지 확인
        Boolean hasNext = hasNextList(users, pageable);
        return new SliceImpl<>(users, pageable, hasNext);
    }

    // 아이디로 유저의 친구들을 조회
    @Override
    public Optional<Slice<User>> findFriendById(Long userId, Integer cursorId) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        List<User> users = query.selectFrom(user)
                .where(user.id.in(
                        JPAExpressions.select(friend.friendId.friendId)
                                .from(friend)
                                .where(friend.friendId.userId.eq(userId))
                ))
                .offset(cursorId)
                .limit(pageable.getPageSize() + 1)
                .orderBy(user.nickname.asc())
                .fetch();

        // 다음 페이지가 있는지 확인
        Boolean hasNext = hasNextList(users, pageable);
        return Optional.of(new SliceImpl<>(users, pageable, hasNext));
    }

    @Override
    public Slice<User> findByNickname(Long userId, String nickname, Integer cursorId) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        QUser user2 = new QUser("user2");

        // email이 비어있는 상태로 전송되었다면 빈 리스트를 반환해준다.
        if(StringHelper.isEmpty(nickname)) {
            return new SliceImpl<>(Collections.emptyList(), pageable, false);
        }

        List<User> friendList = query.select(user2)
                .from(user)
                .join(friend).on(user.id.eq(friend.friendId.userId))
                .join(user2).on(user2.id.eq(friend.friendId.friendId))
                .where(user.id.eq(userId)
                        .and(user2.nickname.like("%" + nickname + "%")))
                .offset(cursorId)
                .limit(pageable.getPageSize() + 1)
                .fetch();

        // 다음 페이지가 있는지 확인
        Boolean hasNext = hasNextList(friendList, pageable);

        return new SliceImpl<>(friendList, pageable, hasNext);
    }

    // 친구가 이미 된 친구가 누군지를 조회하는 서브쿼리
    private JPQLQuery<Long> subQuery(Long userId) {
        return JPAExpressions.select(friend.friendId.friendId)
                .from(friend)
                .where(friend.friendId.userId.eq(userId));
    }

    private <T> Boolean hasNextList(List<T> list, Pageable pageable) {
        boolean hasNext = list.size() > pageable.getPageSize();
        if(hasNext) {
            list.remove(list.size() - 1); // 다음 페이지가 있으면 리스트에서 마지막 요소 제거
        }
        return hasNext;
    }
}
