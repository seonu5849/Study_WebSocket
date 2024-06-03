package com.example.websocket.profile.service;

import com.example.websocket.profile.dto.response.ProfileViewDto;
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
public class ProfileViewService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ProfileViewDto profileView(Long myId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        return ProfileViewDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileImg())
                .backgroundUrl(user.getBackgroundImg())
                .statusMessage(user.getStatusMessage())
                .isMine(myId.equals(user.getId()))
                .build();
    }

}
