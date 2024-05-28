package com.example.websocket.profile.service;

import com.example.websocket.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AddFriendService { // 친구 추가 로직

    private final ProfileRepository profileRepository;

}
