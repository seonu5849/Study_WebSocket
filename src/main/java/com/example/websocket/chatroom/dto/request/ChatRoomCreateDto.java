package com.example.websocket.chatroom.dto.request;

import com.example.websocket.chatroom.domain.ChatRoom;
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

    public ChatRoom toChatRoomEntity() {
        return ChatRoom.builder()
                .title(this.title)
                .profileImg("original-chatroom-profile.png")
                .build();
    }

    public void enterToChatRoom(Long userId) {
        this.userIds.add(userId);
    }
}
