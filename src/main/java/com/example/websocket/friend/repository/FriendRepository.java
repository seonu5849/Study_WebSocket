package com.example.websocket.friend.repository;

import com.example.websocket.friend.domain.Friend;
import com.example.websocket.friend.domain.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
}
