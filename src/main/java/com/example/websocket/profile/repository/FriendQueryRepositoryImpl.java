package com.example.websocket.profile.repository;

import com.example.websocket.profile.domain.Friend;
import com.example.websocket.profile.domain.QFriend;
import com.example.websocket.user.domain.QUser;
import com.example.websocket.user.domain.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.StringHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

    private final JPAQueryFactory query;
    private final QUser user;

    public FriendQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
        this.user = QUser.user;
    }

    @Override
    public Slice<User> findFriendByEmail(Long userId, String email, Pageable pageable) {

        // email이 비어있는 상태로 전송되었다면 빈 리스트를 반환해준다.
        if(StringHelper.isEmpty(email)) {
            return new SliceImpl<>(Collections.emptyList(), pageable, false);
        }

        List<User> users = query.selectFrom(user)
                .where(user.id.ne(userId)
                        .and(user.email.like("%"+email+"%")))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 페이지 사이즈보다 하나 더 가져와서 다음 페이지가 있는지 확인
                .fetch();

        // 다음 페이지가 있는지 확인
        boolean hasNext = users.size() > pageable.getPageSize();
        if(hasNext) {
            users.remove(users.size() - 1); // 다음 페이지가 있으면 리스트에서 마지막 요소 제거
        }

        return new SliceImpl<>(users, pageable, hasNext);
    }
}
