package com.example.websocket.chatroom.dto.request;

import com.example.websocket.chatroom.domain.ChatRoom2;
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

    public ChatRoom2 toChatRoomEntity() {
        return ChatRoom2.builder()
                .title(this.title)
                .build();
    }
}
