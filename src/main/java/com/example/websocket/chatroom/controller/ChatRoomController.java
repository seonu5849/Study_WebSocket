package com.example.websocket.chatroom.controller;

import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatroom.service.ChatRoomListService;
import com.example.websocket.config.security.domain.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomListService chatRoomListService;

    @GetMapping("/chatrooms/list")
    public String chatroomListView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                   Model model) {
        if(principalDetail == null) {
            return "login";
        }
        List<ChatRoomListDto> chatRoomListDtos = chatRoomListService.chatRoomListView(principalDetail.getUser().getId());
        model.addAttribute("chatrooms", chatRoomListDtos);
        return "chatroom-list";
    }

}
