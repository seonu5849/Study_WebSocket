package com.example.websocket.profile.domain;

import com.example.websocket.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FriendId {

    private Long userId;
    private Long friendId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendId friendId = (FriendId) o;
        return Objects.equals(userId, friendId.userId) &&
                Objects.equals(friendId, friendId.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId);
    }

}
