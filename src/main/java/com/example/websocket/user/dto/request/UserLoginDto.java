package com.example.websocket.user.dto.request;

import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginDto {

    private String email;
    private String password;

}
