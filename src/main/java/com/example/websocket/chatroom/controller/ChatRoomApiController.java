package com.example.websocket.chatroom.controller;

import com.example.websocket.chatroom.dto.request.ChatRoomCreateDto;
import com.example.websocket.chatroom.service.ChatRoomCreateService;
import com.example.websocket.chatroom.service.ChatRoomInviteMemberService;
import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.friend.dto.response.FriendDto;
import com.example.websocket.chatroom.service.FindFriendForChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatrooms")
public class ChatRoomApiController {

    private final ChatRoomCreateService chatRoomCreateService;
    private final FindFriendForChatService findFriendForChatService;
    private final ChatRoomInviteMemberService chatRoomInviteMemberService;

    @PostMapping("/create")
    public ResponseEntity<Long> createChatRoom(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                   @RequestBody ChatRoomCreateDto chatRoomCreateDto) {
        log.info("createChatRoom({}) invoked.", chatRoomCreateDto);
        Long chatRoomId = chatRoomCreateService.createChatRoom(principalDetail.getUser().getId(), chatRoomCreateDto);

        return ResponseEntity.ok(chatRoomId);
    }

    @Operation(summary = "채팅방 내에서 친구 검색 API")
    @GetMapping("{chatRoomId}/friends")
    public ResponseEntity<FriendDto> findFriendsForChat(@PathVariable("chatRoomId") Long chatRoomId,
                                                        String nickname,
                                                        @RequestParam(defaultValue = "0") Integer cursorId) {
        log.debug("findFriendsForChat({}, {}, {}) invoked.", chatRoomId, nickname, cursorId);
        FriendDto friendForChat = findFriendForChatService.findFriendForChat(chatRoomId, nickname, cursorId);
        return ResponseEntity.ok(friendForChat);
    }

    @Operation(summary = "채팅방에 친구 초대")
    @PostMapping("/{chatRoomId}/invite")
    public ResponseEntity<Long> inviteMember(@PathVariable("chatRoomId") Long chatRoomId,
                                             @RequestBody List<Long> userIds) {
        log.debug("inviteMember({}, {}) invoked.", chatRoomId, userIds);
        chatRoomInviteMemberService.inviteMemberForChatRoom(chatRoomId, userIds);
        return ResponseEntity.ok(chatRoomId);
    }

}
