package com.example.websocket.user.controller;

import com.example.websocket.user.dto.request.UserRegisterDto;
import com.example.websocket.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController { // 화면을 담당

    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("user", new UserRegisterDto());
        return "register";
    }

    @GetMapping("/login")
    public String loginView(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login";
    }

}
