package com.example.websocket.chatting.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor
public class ChatMemberDto {

    private Long userId;
    private String profileUrl;
    private String nickname;

    @Builder
    public ChatMemberDto(Long userId, String profileUrl, String nickname) {
        this.userId = userId;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }
}
