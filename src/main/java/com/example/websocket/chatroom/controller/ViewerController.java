package com.example.websocket.chatroom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewerController {

    @GetMapping("/chatroom/list")
    public String chatroomListView() {
        return "chatroom-list";
    }

    @GetMapping("/chatroom")
    public String chatroomView() {
        return "chatroom";
    }

}
