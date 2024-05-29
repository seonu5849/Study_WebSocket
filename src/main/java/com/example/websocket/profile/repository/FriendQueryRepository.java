package com.example.websocket.profile.repository;

import com.example.websocket.profile.domain.Friend;
import com.example.websocket.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FriendQueryRepository {

    Slice<User> findFriendByEmail(Long userId, String email, Integer cursorId);

}
