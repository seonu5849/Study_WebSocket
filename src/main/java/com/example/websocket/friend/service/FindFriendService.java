package com.example.websocket.friend.service;

import com.example.websocket.friend.dto.response.FriendDto;
import com.example.websocket.friend.dto.response.UserInfo;
import com.example.websocket.friend.repository.FriendQueryRepository;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.exception.ErrorStatus;
import com.example.websocket.user.exception.UserException;
import com.example.websocket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FindFriendService {

    private final UserRepository userRepository;
    private final FriendQueryRepository friendQueryRepository;

    @Transactional(readOnly = true)
    public FriendDto findFriends(Long userId, String nickname, Integer cursorId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        Slice<User> findFriendList =
                friendQueryRepository.findByNickname(user.getId(), nickname, cursorId);

        return FriendDto.builder()
                .userInfo(findFriendList.stream()
                        .map(u -> UserInfo.builder()
                                .userId(u.getId())
                                .profileUrl(u.getProfileImg())
                                .nickname(u.getNickname())
                                .build()).toList())
                .hasNext(findFriendList.hasNext())
                .build();
    }

}
