package com.example.websocket.profile.dto.response;

import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {
    private Long userId;
    private String nickname;
    private String profileUrl;
}
