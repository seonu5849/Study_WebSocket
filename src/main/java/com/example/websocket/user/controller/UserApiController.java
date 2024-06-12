package com.example.websocket.user.controller;

import com.example.websocket.config.response.BaseResponse;
import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.dto.request.UserRegisterDto;
import com.example.websocket.user.dto.response.UserInfoDto;
import com.example.websocket.user.service.UserResisterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserApiController {

    private final UserResisterService userResisterService;


    @Operation(summary = "아이디 중복체크 API")
    @PostMapping("/register/duplicate")
    public ResponseEntity<Boolean> duplicate(String email) {
        log.debug("email : {}", email);
        return ResponseEntity.ok(userResisterService.checkDuplicateEmail(email));
    }

    @Operation(summary = "회원가입 API")
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

    @Operation(summary = "유저 정보 API")
    @GetMapping("/userInfo")
    public ResponseEntity<UserInfoDto> userInfo(@AuthenticationPrincipal PrincipalDetail principalDetail) {
        log.info("userInfo: {}", principalDetail.getUser());
        User user = principalDetail.getUser();
        return ResponseEntity.ok()
                .body(new UserInfoDto(user.getId(), user.getNickname(), user.getProfileImg()));
    }

}