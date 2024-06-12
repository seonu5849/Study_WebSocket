package com.example.websocket.chatroom.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomListDto {

    private Long chatRoomId;
    private String title;
    private String profileImg;
    private Long personCount;
    private LocalDateTime lastCommentDate;
    private String lastComment;

}
