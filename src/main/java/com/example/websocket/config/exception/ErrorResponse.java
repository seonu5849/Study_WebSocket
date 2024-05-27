package com.example.websocket.config.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorResponse {

    @Schema(example = "에러 메시지")
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
