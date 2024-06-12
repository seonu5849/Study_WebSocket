package com.example.websocket.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {

    private Long id;
    private String nickname;
    private String profileImg;

    public UserInfoDto(Long id, String nickname, String profileImg) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
