package com.example.websocket.friend.controller;

import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.friend.dto.response.FriendDto;
import com.example.websocket.friend.service.AddFriendService;
import com.example.websocket.friend.service.SearchFriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FriendApiController {

    private final SearchFriendService searchFriendService;
    private final AddFriendService addFriendService;

    @Operation(description = "친구 검색 시 출력 리스트")
    @GetMapping("/friends/search")
    public ResponseEntity<FriendDto> friendView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                      Integer cursorId,
                                                      String email,
                                                      Model model) {
        FriendDto searchFriendList =
                searchFriendService.searchFriendList(principalDetail.getUser().getId(), cursorId, email);

        return ResponseEntity.ok()
                .body(searchFriendList);
    }

    @PostMapping("/friend")
    public ResponseEntity<Boolean> addFriend(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                            Long friendId) {
        log.debug("userId: {}, friendId: {}", principalDetail.getUser().getId(), friendId);
        Boolean isSave = addFriendService.addFriends(principalDetail.getUser().getId(), friendId);
        return ResponseEntity.ok(isSave);
    }

}
