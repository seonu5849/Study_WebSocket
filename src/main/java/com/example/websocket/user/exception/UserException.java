package com.example.websocket.user.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException{

    private final ErrorStatus errorStatus;

    public UserException(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }
}
