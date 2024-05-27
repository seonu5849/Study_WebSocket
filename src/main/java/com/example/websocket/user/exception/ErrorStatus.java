package com.example.websocket.user.exception;

import lombok.Getter;

@Getter
public enum ErrorStatus {

    DUPLICATE_USER(401, "사용중인 아이디입니다.");

    private final int status;
    private final String message;

    ErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
