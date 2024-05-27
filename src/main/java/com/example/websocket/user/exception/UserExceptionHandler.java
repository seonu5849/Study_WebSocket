package com.example.websocket.user.exception;

import com.example.websocket.config.exception.ErrorResponse;
import com.example.websocket.user.controller.UserApiController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserApiController.class)
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> duplicateExceptionHanlder(UserException e) {
        return ResponseEntity
                .status(e.getErrorStatus().getStatus())
                .body(new ErrorResponse(e.getErrorStatus().getMessage()));
    }

}
