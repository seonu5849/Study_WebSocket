package com.example.websocket.chatting.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ChatId implements Serializable {

    private Long chatId;
    private Long chatroomId;

    // hashCode and equals 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatId that = (ChatId) o;
        return Objects.equals(chatId, that.chatId) &&
                Objects.equals(chatroomId, that.chatroomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, chatroomId);
    }

}
