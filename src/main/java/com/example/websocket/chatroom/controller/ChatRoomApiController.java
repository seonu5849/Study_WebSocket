package com.example.websocket.chatroom.controller;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.dto.request.ChatRoomCreateDto;
import com.example.websocket.chatroom.service.ChatRoomCreateService;
import com.example.websocket.config.security.domain.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatrooms")
public class ChatRoomApiController {

    private final ChatRoomCreateService chatRoomCreateService;

    @PostMapping("/create")
    public ResponseEntity<Long> createChatRoom(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                   @RequestBody ChatRoomCreateDto chatRoomCreateDto) {
        log.info("createChatRoom({}) invoked.", chatRoomCreateDto);
        Long chatRoomId = chatRoomCreateService.createChatRoom(principalDetail.getUser().getId(), chatRoomCreateDto);

        return ResponseEntity.ok(chatRoomId);
    }

}
