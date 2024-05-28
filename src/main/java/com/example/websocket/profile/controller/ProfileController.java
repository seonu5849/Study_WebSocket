package com.example.websocket.profile.controller;

import com.example.websocket.config.security.domain.PrincipalDetail;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profileView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                              Model model) {
        if(principalDetail == null) {
            return "login";
        }

        model.addAttribute("myInfo", principalDetail.getUser());

        return "profile";
    }
}
