package com.example.websocket.chatting.dto;

import com.example.websocket.config.utils.DateUtils;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@NoArgsConstructor
public class ChatMessageDto {

    public enum MessageType{
        ENTER, TALK
    }

    private MessageType messageType; // 메시지 타입
    private Long chatRoomId; // 방 번호
    private UserInfo userInfo; // 채팅을 보낸 사람의 정보
    private String message; // 메시지
    private String sendTime; // 보낸 시간

    public ChatMessageDto(MessageType messageType, Long chatRoomId, UserInfo userInfo, String message, String sendTime) {
        this.messageType = messageType;
        this.chatRoomId = chatRoomId;
        this.userInfo = userInfo;
        this.message = message;
        this.sendTime = sendTime;
    }

    @ToString
    @Getter
    @NoArgsConstructor
    public static class UserInfo {
        private Long id;
        private String nickname;
        private String profileImg;

        public UserInfo(Long id, String nickname, String profileImg) {
            this.id = id;
            this.nickname = nickname;
            this.profileImg = profileImg;
        }
    }
}
