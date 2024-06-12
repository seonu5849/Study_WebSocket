package com.example.websocket.chatroom.exception;

import lombok.Getter;

@Getter
public class ChatRoomException extends RuntimeException{

    private final ErrorStatus errorStatus;

    public ChatRoomException(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }
}
