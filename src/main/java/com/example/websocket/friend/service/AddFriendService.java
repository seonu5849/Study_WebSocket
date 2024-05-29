package com.example.websocket.friend.service;

import com.example.websocket.friend.repository.FriendRepository;
import com.example.websocket.friend.domain.Friend;
import com.example.websocket.friend.domain.FriendId;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.exception.ErrorStatus;
import com.example.websocket.user.exception.UserException;
import com.example.websocket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddFriendService { // 친구 추가 로직

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public Boolean addFriends(Long userId, Long friendId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        User findFriend = userRepository.findById(friendId)
                .orElseThrow(() -> new UserException(ErrorStatus.FRIEND_NOT_FOUND));

        FriendId friendIds = new FriendId(findUser.getId(), findFriend.getId());
        Friend saveFriend = Friend.builder()
                .friendId(friendIds)
                .user(findUser)
                .friend(findFriend)
                .build();
        Friend save = friendRepository.save(saveFriend);

        Long saveUserId = save.getFriendId().getUserId();
        Long saveFriendId = save.getFriendId().getFriendId();
        log.info("saveUserId: {}, saveFriendId: {}", saveUserId, saveFriendId);

        return saveUserId != null && saveFriendId != null; // 저장에 성공하면 true, 실패하면 false
    }

}
