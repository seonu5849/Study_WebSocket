package com.example.websocket.chatroom.dto.response;

import com.example.websocket.config.utils.TimeFormatUtils;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@NoArgsConstructor
public class ChatRoomListDto {

    private Long chatRoomId;
    private String title;
    private String profileImg;
    private Long personCount;
    private String lastCommentDate;
    private String lastComment;

    @Builder
    public ChatRoomListDto(Long chatRoomId, String title, String profileImg, Long personCount, String lastCommentDate, String lastComment) {
        this.chatRoomId = chatRoomId;
        this.title = title;
        this.profileImg = profileImg;
        this.personCount = personCount;
        this.lastCommentDate = lastCommentDate;
        this.lastComment = lastComment;
    }

    public void formatLastCommentDate(String dateString) {
        this.lastCommentDate = TimeFormatUtils.formatTime(dateString);
    }
}
