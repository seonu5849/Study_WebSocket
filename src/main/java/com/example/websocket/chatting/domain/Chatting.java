package com.example.websocket.chatting.domain;

import com.example.websocket.chatroom.domain.ChatRoom2;
import com.example.websocket.config.domain.BaseTimeEntity;
import com.example.websocket.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Chatting extends BaseTimeEntity {

    /**
     * 복합키 매핑이면서 외래키를 기본키로 사용해야 하는 경우
     * @EmbeddedId, @Embeddable을 통해 먼저 복합 기본키를 설정하고
     * @MapsId를 통해 외래키 지정을 한다.
     */

    @EmbeddedId
    private ChatId chatId;

    @MapsId("chatroomId")
    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom2 chatroom2;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String comment;

    @Column(name = "MESSAGE_CONFIRM")
    private boolean isChecked;

}
