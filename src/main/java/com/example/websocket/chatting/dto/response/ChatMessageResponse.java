package com.example.websocket.chatting.dto.response;

import com.example.websocket.chatting.domain.Chat;
import com.example.websocket.chatting.domain.MessageType;
import com.example.websocket.config.utils.TimeFormatUtils;
import com.example.websocket.user.domain.User;
import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageResponse {

    private Long userId;
    private String nickname;
    private String profileImg;
    private String comment;
    private String sendTime;
    private boolean isMine;
    private MessageType messageType;

    @Builder
    public ChatMessageResponse(Long userId, String nickname, String profileImg, String comment, String sendTime, boolean isMine, MessageType messageType) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.comment = comment;
        this.sendTime = sendTime;
        this.isMine = isMine;
        this.messageType = messageType;
    }

    // queryDsl mapping 생성자
    public ChatMessageResponse(Long userId, String nickname, String profileImg, String comment, String sendTime, MessageType messageType) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.comment = comment;
        this.sendTime = sendTime;
        this.messageType = messageType;
    }

    public static ChatMessageResponse of(Chat chat) {
        User user = chat.getUser();
        return ChatMessageResponse.builder()
                .userId(chat.getId().getChatId())
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .comment(chat.getComment())
                .sendTime(chat.getCreateDate())
                .messageType(chat.getMessageType())
                .build();
    }

    public static List<ChatMessageResponse> of(List<Chat> chats) {
        return chats.stream()
                .map(chat -> ChatMessageResponse.builder()
                            .comment(chat.getComment())
                            .sendTime(chat.getCreateDate())
                            .messageType(chat.getMessageType())
                            .build()
                ).toList();
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
