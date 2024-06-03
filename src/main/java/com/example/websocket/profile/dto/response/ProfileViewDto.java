package com.example.websocket.profile.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileViewDto {

    private Long userId;
    private String nickname;
    private String profileUrl;
    private String backgroundUrl;
    private String statusMessage;
    private boolean isMine;

}
