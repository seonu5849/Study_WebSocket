package com.example.websocket.chatroom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewerController {

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/main")
    public String mainView() {
        return "main";
    }

}
