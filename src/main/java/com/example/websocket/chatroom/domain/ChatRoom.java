package com.example.websocket.chatroom.domain;

import com.example.websocket.config.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CHATROOM")
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "CHATROOM_ID")
    private Long id;
    private String title;
    private String profileImg;

    @Builder.Default // 빌더에 포함되지 않도록 설정
    @Transient // 컬럼으로 매핑하지 않도록 설정
    private Set<WebSocketSession> sessions = new HashSet<>(); // 채팅방에 참가 중인 유저들의 세션 아이디

    @Builder.Default
    @OneToMany(mappedBy = "chatroom", fetch = FetchType.LAZY)
    private List<InviteChat> inviteChatList = new ArrayList<>();
}
