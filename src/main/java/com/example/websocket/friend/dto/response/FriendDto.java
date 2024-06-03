package com.example.websocket.friend.dto.response;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendDto {

    private List<UserInfo> userInfo;
    private boolean hasNext; // 마지막 페이지인지 아닌지를 정함

}


