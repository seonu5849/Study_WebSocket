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
public class SearchFriendService {

    private final UserRepository userRepository;
    private final FriendQueryRepository friendQueryRepository;

    // 무한 스크롤 구현을 위한 Slice
    @Transactional(readOnly = true)
    public FriendDto searchFriendList(Long userId, Integer cursorId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        Slice<User> slice = friendQueryRepository.findFriendByEmail(user.getId(), email, cursorId);

        return FriendDto.builder()
                        .userInfo(slice.stream()
                                .map(u -> UserInfo.builder()
                                        .userId(u.getId())
                                        .nickname(u.getNickname())
                                        .profileUrl(u.getProfileImg())
                                        .build())
                                .toList())
                .hasNext(slice.hasNext())
                .build();

    }

}
