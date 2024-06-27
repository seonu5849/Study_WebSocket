package com.example.websocket.chatting.dto.response;

import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomDto {

    private String title;
    private Integer memberCount;
    private List<ChatMessageResponse> messages;

    @Builder
    public ChatRoomDto(String title, Integer memberCount, List<ChatMessageResponse> messages) {
        this.title = title;
        this.memberCount = memberCount;
        this.messages = messages;
    }
}
