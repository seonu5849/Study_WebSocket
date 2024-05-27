package com.example.websocket.chatroom.domain;

import com.example.websocket.config.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CHATROOM")
public class Chatroom2 extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "CHATROOM_ID")
    private Long id;
    private String title;
    private String profileImg;

}
