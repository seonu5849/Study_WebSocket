package com.example.websocket.friend.service;

import com.example.websocket.friend.dto.response.FriendDto;
import com.example.websocket.friend.dto.response.UserInfo;
import com.example.websocket.friend.repository.FriendQueryRepository;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.exception.ErrorStatus;
import com.example.websocket.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FriendListService {

    private final FriendQueryRepository friendQueryRepository;

    @Transactional(readOnly = true)
    public FriendDto profileFriendList(Long userId, Integer cursorId) {
        Slice<User> users = friendQueryRepository.findFriendById(userId, cursorId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        return FriendDto.builder()
                .userInfo(users.stream()
                        .map(user -> UserInfo.builder()
                                .userId(user.getId())
                                .nickname(user.getNickname())
                                .profileUrl(user.getProfileImg())
                                .statusMessage(user.getStatusMessage())
                                .build())
                        .toList())
                .hasNext(users.hasNext())
                .build();
    }

}
