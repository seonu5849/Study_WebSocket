package com.example.websocket.profile.service;

import com.example.websocket.profile.dto.response.FriendDto;
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
    private final static int PAGE_SIZE = 5;
    private final FriendQueryRepository friendQueryRepository;

    // 무한 스크롤 구현을 위한 Slice
    @Transactional(readOnly = true)
    public List<FriendDto> searchFriendList(Long userId, Integer cursorId, String email) {
        Pageable pageable = PageRequest.of(cursorId, PAGE_SIZE);
        Slice<User> slice = friendQueryRepository.findFriendByEmail(userId, email, pageable);
        log.info("users: {}", slice);

        return slice.stream()
                .map(user -> FriendDto.builder()
                        .nickname(user.getNickname())
                        .profileUrl(user.getProfileImg())
                        .build()).toList();
    }

}
