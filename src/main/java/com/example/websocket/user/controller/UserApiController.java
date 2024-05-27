package com.example.websocket.user.controller;

import com.example.websocket.config.response.BaseResponse;
import com.example.websocket.user.dto.request.UserRegisterDto;
import com.example.websocket.user.service.UserResisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserResisterService userResisterService;

    @PostMapping("/register/duplicate")
    public ResponseEntity<Boolean> duplicate(String email) {
        log.debug("email : {}", email);
        return ResponseEntity.ok(userResisterService.checkDuplicateEmail(email));
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody UserRegisterDto userRegisterDto) {
        log.debug("userRegisterDto: {}", userRegisterDto);

        BaseResponse baseResponse = null;
        Long userId = userResisterService.register(userRegisterDto);
        if(userId != null) {
            baseResponse = new BaseResponse("회원가입이 성공했습니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(baseResponse);
    }

}