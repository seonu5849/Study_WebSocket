package com.example.websocket.user.dto.request;

import com.example.websocket.user.domain.User;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    private String email;
    private String nickname;
    private String password;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .build();
    }

    public void encodePassword(String password) {
        this.password = password;
    }

}
