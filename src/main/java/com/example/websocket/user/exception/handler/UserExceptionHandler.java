package com.example.websocket.user.exception.handler;

import com.example.websocket.config.exception.ErrorResponse;
import com.example.websocket.user.controller.UserApiController;
import com.example.websocket.user.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserApiController.class)
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> duplicateExceptionHandler(UserException e) {
        return ResponseEntity
                .status(e.getErrorStatus().getStatus())
                .body(new ErrorResponse(e.getErrorStatus().getMessage()));
    }

}
