package com.example.websocket.chatroom.domain;

import com.example.websocket.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InviteChat {

    @Id
    @GeneratedValue
    @Column(name = "INVITE_CHAT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private Chatroom2 chatroom;

}
