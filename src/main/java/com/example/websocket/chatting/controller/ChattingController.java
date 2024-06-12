package com.example.websocket.chatting.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChattingController {

    @GetMapping("/chatrooms/{chatRoomId}")
    public String chattingView(@PathVariable("chatRoomId") Long chatRoomId, Model model) {
        model.addAttribute("chatRoomId", chatRoomId);

        return "chatroom";
    }

}
