package com.example.websocket.chatting.controller;

import com.example.websocket.chatting.dto.response.ChatMemberDto;
import com.example.websocket.chatting.service.ChatMemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chatrooms")
public class ChatApiController {

    private final ChatMemberService chatMemberService;

    @GetMapping("/{chatRoomId}/members")
    public ResponseEntity<List<ChatMemberDto>> chatMember(@PathVariable("chatRoomId") Long chatRoomId) {
        List<ChatMemberDto> chatMember = chatMemberService.findChatMember(chatRoomId);
        return ResponseEntity.ok(chatMember);
    }

}
