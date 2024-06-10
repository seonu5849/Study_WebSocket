package com.example.websocket.chatroom.controller;

import com.example.websocket.config.security.domain.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewerController {

    @GetMapping("/chatroom/list")
    public String chatroomListView(@AuthenticationPrincipal PrincipalDetail principalDetail) {
        if(principalDetail == null) {
            return "login";
        }

        return "chatroom-list";
    }

    @GetMapping("/chatroom")
    public String chatroomView() {
        return "chatroom";
    }

}
