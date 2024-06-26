package com.example.websocket.chatting.dto.response;

import com.example.websocket.chatting.domain.MessageType;
import com.example.websocket.config.utils.TimeFormatUtils;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageDto {

    private Long userId;
    private String nickname;
    private String profileImg;
    private String comment;
    private String sendTime;
    private boolean isMine;
    private MessageType messageType;

    @Builder
    public ChatMessageDto(Long userId, String nickname, String profileImg, String comment, String sendTime, boolean isMine, MessageType messageType) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.comment = comment;
        this.sendTime = sendTime;
        this.isMine = isMine;
        this.messageType = messageType;
    }

    // queryDsl mapping 생성자
    public ChatMessageDto(Long userId, String nickname, String profileImg, String comment, String sendTime, MessageType messageType) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.comment = comment;
        this.sendTime = sendTime;
        this.messageType = messageType;
    }

    public void updateMessageOwner(Long userId) {
        this.isMine = this.userId != null ? this.userId.equals(userId) : false;
    }

    public void formatLastCommentDate(String dateString) {
        this.sendTime = TimeFormatUtils.formatTime(dateString);
    }

    // 줄바꿈 문자를 <br>로 바꿔주는 메소드
    public String getFormattedComment() {
        return this.comment != null ? this.comment.replace("\n", "<br>") : null;
    }

    public String isMessageType() {
        return this.messageType == MessageType.ENTER ? "ENTER" : "TALK";
    }
}
