package com.example.websocket.chatroom.dto.response;

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
        this.lastCommentDate = formatTime(dateString);
    }

    private String formatTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        return dateTime.format(DateTimeFormatter.ofPattern("a hh:mm"));
    }
}
