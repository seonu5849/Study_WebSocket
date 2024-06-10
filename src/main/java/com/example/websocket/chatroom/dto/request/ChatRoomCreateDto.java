package com.example.websocket.chatroom.dto.request;

import com.example.websocket.chatroom.domain.Chatroom2;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomCreateDto {

    private String title;
    private List<Long> userIds;

    public Chatroom2 toChatRoomEntity() {
        return Chatroom2.builder()
                .title(this.title)
                .build();
    }
}
