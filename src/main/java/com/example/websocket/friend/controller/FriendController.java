package com.example.websocket.friend.controller;

import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.friend.dto.response.FriendDto;
import com.example.websocket.friend.service.FriendListService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpClient;

@RequiredArgsConstructor
@Controller
public class FriendController {

    private final FriendListService friendListService;

    @GetMapping("/profile")
    public String profileView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                              HttpServletResponse response,
                              Model model) {
        if(principalDetail == null) {
            return "login";
        }

        FriendDto friendDto = friendListService.profileFriendList(principalDetail.getUser().getId(), 0);

        model.addAttribute("myInfo", principalDetail.getUser());
        model.addAttribute("friendList", friendDto);

        return "profile";
    }
}
