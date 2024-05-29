package com.example.websocket.profile.controller;

import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.profile.dto.response.FriendDto;
import com.example.websocket.profile.service.SearchFriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProfileApiController {

    private final SearchFriendService searchFriendService;

    @Operation(description = "친구 검색 시 출력 리스트")
    @GetMapping("/friend")
    public ResponseEntity<FriendDto> friendView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                      Integer cursorId,
                                                      String email,
                                                      Model model) {
        log.debug("user: {}", principalDetail.getUser());
        log.debug("cursorId: {}, email: {}",cursorId, email);
        FriendDto searchFriendList = searchFriendService.searchFriendList(principalDetail.getUser().getId(), cursorId, email);

        log.info("friends: {}", searchFriendList);
        return ResponseEntity.ok()
                .body(searchFriendList);
    }

    @PostMapping("/friend")
    public ResponseEntity<String> addFriend() {

        return null;
    }

}
