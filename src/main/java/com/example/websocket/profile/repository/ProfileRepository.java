package com.example.websocket.profile.repository;

import com.example.websocket.profile.domain.Friend;
import com.example.websocket.profile.domain.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Friend, FriendId> {
}
