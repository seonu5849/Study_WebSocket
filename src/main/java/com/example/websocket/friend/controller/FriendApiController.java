package com.example.websocket.friend.controller;

import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.file.utils.FileUtil;
import com.example.websocket.friend.dto.response.FriendDto;
import com.example.websocket.friend.service.AddFriendService;
import com.example.websocket.friend.service.FindFriendService;
import com.example.websocket.friend.service.FriendListService;
import com.example.websocket.friend.service.SearchFriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/friends")
public class FriendApiController {

    private final SearchFriendService searchFriendService;
    private final FriendListService friendListService;
    private final AddFriendService addFriendService;
    private final FindFriendService findFriendService;

    @Operation(summary = "친구 목록 - 무한스크롤 API")
    @GetMapping("/list")
    public ResponseEntity<FriendDto> friendListView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                    Integer cursorId) {
        log.debug("friendListView({}, {}) invoked.", principalDetail.getUser().getId(), cursorId);
        FriendDto friendDto = friendListService.profileFriendList(principalDetail.getUser().getId(), cursorId);
        log.info("friendDto: {}", friendDto);
        return ResponseEntity.ok()
                .body(friendDto);
    }

    @Operation(summary = "친구 검색 시 출력 리스트")
    @GetMapping("/search")
    public ResponseEntity<FriendDto> friendSearchView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                      Integer cursorId,
                                                      String email,
                                                      Model model) {
        log.debug("friendSearchView({}, {}, {}) invoked.", principalDetail.getUser().getId(), cursorId, email);
        FriendDto searchFriendList =
                searchFriendService.searchFriendList(principalDetail.getUser().getId(), cursorId, email);

        return ResponseEntity.ok()
                .body(searchFriendList);
    }

    @Operation(summary = "친구 추가 API")
    @PostMapping("/{friendId}")
    public ResponseEntity<Boolean> addFriend(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                             @PathVariable Long friendId) {
        log.debug("userId: {}, friendId: {}", principalDetail.getUser().getId(), friendId);
        Boolean isSave = addFriendService.addFriends(principalDetail.getUser().getId(), friendId);
        return ResponseEntity.ok(isSave);
    }

    @Operation(summary = "친구 검색 API")
    @GetMapping("/find")
    public ResponseEntity<FriendDto> findFriends(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                 String nickname,
                                                 @RequestParam(defaultValue = "0") Integer cursorId) {
        log.debug("findFriends({}, {}) invoked.", nickname, cursorId);
        FriendDto findFriendList =
                findFriendService.findFriends(principalDetail.getUser().getId(), nickname, cursorId);
        log.info("findFriendList: {}", findFriendList);
        return ResponseEntity.ok(findFriendList);
    }

    @Operation(summary = "로컬 프로필 사진 출력 API")
    @GetMapping("/profile/{fileName}")
    public Resource printImage(@PathVariable String fileName) throws MalformedURLException {
        log.info("printImage({}) invoked.", fileName);
        return new UrlResource("file", FileUtil.getFullPath(fileName));
    }

}
