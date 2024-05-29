package com.example.websocket.profile.service;

import com.example.websocket.profile.dto.response.FriendDto;
import com.example.websocket.profile.dto.response.UserInfo;
import com.example.websocket.profile.repository.FriendQueryRepository;
import com.example.websocket.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchFriendService {

    private final FriendQueryRepository friendQueryRepository;

    // 무한 스크롤 구현을 위한 Slice
    @Transactional(readOnly = true)
    public FriendDto searchFriendList(Long userId, Integer cursorId, String email) {
        Slice<User> slice = friendQueryRepository.findFriendByEmail(userId, email, cursorId);
        log.info("users: {}", slice.getContent());

        return FriendDto.builder()
                        .userInfo(slice.stream()
                                .map(user -> UserInfo.builder()
                                        .userId(user.getId())
                                        .nickname(user.getNickname())
                                        .profileUrl(user.getProfileImg())
                                        .build())
                                .toList())
                .isLast(slice.isLast())
                .build();

    }

}
