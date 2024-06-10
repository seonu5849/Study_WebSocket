package com.example.websocket.chatroom.domain;

import com.example.websocket.friend.domain.FriendId;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class InviteChatId implements Serializable {

    private Long userId;
    private Long chatRoomId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InviteChatId inviteChatId = (InviteChatId) o;
        return Objects.equals(userId, inviteChatId.userId) &&
                Objects.equals(chatRoomId, inviteChatId.chatRoomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, chatRoomId);
    }

}
