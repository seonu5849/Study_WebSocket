package com.example.websocket.chatting.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomDto {

    private String title;
    private Integer memberCount;
    private List<ChatMessageDto> messages;

    @Builder
    public ChatRoomDto(String title, Integer memberCount, List<ChatMessageDto> messages) {
        this.title = title;
        this.memberCount = memberCount;
        this.messages = messages;
    }
}
