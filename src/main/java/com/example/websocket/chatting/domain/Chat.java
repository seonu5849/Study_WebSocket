package com.example.websocket.chatting.domain;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.config.domain.BaseTimeEntity;
import com.example.websocket.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Chat {

    /**
     * 복합키 매핑이면서 외래키를 기본키로 사용해야 하는 경우
     * @EmbeddedId, @Embeddable을 통해 먼저 복합 기본키를 설정하고
     * @MapsId를 통해 외래키 지정을 한다.
     */

    @EmbeddedId
    private ChatId id;

    @MapsId("chatRoomId")
    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatroom;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String comment;

    @Column(name = "MESSAGE_CONFIRM")
    private boolean isChecked; // 확인 했는지 안했는지

    private String createDate;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Builder
    public Chat(ChatId id, ChatRoom chatroom, User user, String comment, boolean isChecked, String createDate, MessageType messageType) {
        this.id = id;
        this.chatroom = chatroom;
        this.user = user;
        this.comment = comment;
        this.isChecked = isChecked;
        this.createDate = createDate;
        this.messageType = messageType;
    }
}
