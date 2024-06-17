package com.example.websocket.chatting.controller;

import com.example.websocket.config.security.domain.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    @GetMapping("/chatrooms/{chatRoomId}")
    public String chattingView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                               @PathVariable("chatRoomId") Long chatRoomId, Model model) {
        if(principalDetail == null) {
            return "login";
        }

        model.addAttribute("chatRoomId", chatRoomId);

        return "chatroom";
    }

}
