package com.example.websocket.friend.repository;

import com.example.websocket.user.domain.User;
import org.springframework.data.domain.Slice;

public interface FriendQueryRepository {

    Slice<User> findFriendByEmail(Long userId, String email, Integer cursorId);

}
