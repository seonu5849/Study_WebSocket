package com.example.websocket.friend.repository;

import com.example.websocket.user.domain.User;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface FriendQueryRepository {

    Slice<User> findFriendByEmail(Long userId, String email, Integer cursorId);
    Optional<Slice<User>> findFriendById(Long userId, Integer cursorId);

    Slice<User> findByNickname(Long userId, String nickname, Integer cursorId);

}
