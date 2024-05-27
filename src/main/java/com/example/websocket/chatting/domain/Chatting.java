package com.example.websocket.chatting.domain;

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

    @EmbeddedId
    private ChatId chatId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String comment;

    @Column(name = "MESSAGE_CONFIRM")
    private boolean isChecked;

}
