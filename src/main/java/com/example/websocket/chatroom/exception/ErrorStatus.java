package com.example.websocket.chatroom.exception;

import lombok.Getter;

@Getter
public enum ErrorStatus {

    NOT_FOUND_CHATROOM(404, "존재하지 않는 채팅방입니다."),
    NO_ACTIVE_USER_FOUND(404, "접속중인 유저를 찾을 수 없습니다.");

    private final int status;
    private final String message;

    ErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
