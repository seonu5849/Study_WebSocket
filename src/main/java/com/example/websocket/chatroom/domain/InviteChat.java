package com.example.websocket.chatroom.domain;

import com.example.websocket.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

//@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InviteChat {

    @EmbeddedId
    private InviteChatId inviteChatId;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @MapsId("chatRoomId")
    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatroom;

}
