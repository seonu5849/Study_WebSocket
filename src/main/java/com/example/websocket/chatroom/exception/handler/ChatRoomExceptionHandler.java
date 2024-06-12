package com.example.websocket.chatroom.exception.handler;

import com.example.websocket.chatting.handler.WebSocketChatHandler;
import com.example.websocket.config.exception.ErrorResponse;
import com.example.websocket.chatroom.controller.ChatRoomApiController;
import com.example.websocket.chatroom.controller.ChatRoomController;
import com.example.websocket.chatroom.exception.ChatRoomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {ChatRoomController.class, ChatRoomApiController.class, WebSocketChatHandler.class})
public class ChatRoomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> duplicateExceptionHandler(ChatRoomException e) {
        return ResponseEntity
                .status(e.getErrorStatus().getStatus())
                .body(new com.example.websocket.config.exception.ErrorResponse(e.getErrorStatus().getMessage()));
    }

}
