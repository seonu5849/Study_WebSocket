package com.example.websocket.profile.domain;

import com.example.websocket.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Friend {

    @EmbeddedId
    private FriendId friendId;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @MapsId("friendId")
    @ManyToOne
    @JoinColumn(name = "FRIEND_ID")
    private User friend;
}
