package com.example.websocket.chatting.dto.response;

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

    @Builder
    public ChatMessageDto(Long userId, String nickname, String profileImg, String comment, String sendTime, boolean isMine) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.comment = comment;
        this.sendTime = sendTime;
        this.isMine = isMine;
    }

    // queryDsl mapping 생성자
    public ChatMessageDto(Long userId, String nickname, String profileImg, String comment, String sendTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.comment = comment;
        this.sendTime = sendTime;
    }

    public void updateMessageOwner(Long userId) {
        this.isMine = this.userId.equals(userId);
    }

    public void formatLastCommentDate(String dateString) {
        this.sendTime = TimeFormatUtils.formatTime(dateString);
    }
}
