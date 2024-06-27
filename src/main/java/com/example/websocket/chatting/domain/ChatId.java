package com.example.websocket.chatting.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ChatId implements Serializable {

    private Long chatId;
    private Long chatRoomId;

    // hashCode and equals 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatId that = (ChatId) o;
        return Objects.equals(chatId, that.chatId) &&
                Objects.equals(chatRoomId, that.chatRoomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, chatRoomId);
    }

}
