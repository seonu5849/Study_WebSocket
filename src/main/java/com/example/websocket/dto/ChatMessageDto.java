package com.example.websocket.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    public enum MessageType{
        ENTER, TALK
    }

    private MessageType messageType; // 메시지 타입
    private String chatRoomId; // 방 번호
    private Long senderId; // 채팅을 보낸 사람 아이디
    @Setter
    private String message; // 메시지

}
