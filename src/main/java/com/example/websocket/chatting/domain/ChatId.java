package com.example.websocket.chatting.domain;

import com.example.websocket.chatroom.domain.Chatroom2;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ChatId implements Serializable {

    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private Chatroom2 chatroom;

}
