package com.example.websocket.profile.controller;

import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.profile.dto.response.ProfileViewDto;
import com.example.websocket.profile.service.ProfileViewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileApiController {

    private final ProfileViewService profileViewService;

    @Operation(description = "프로필 조회 API")
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileViewDto> profileView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                      @PathVariable Long userId) {

        ProfileViewDto profileViewDto = profileViewService.profileView(principalDetail.getUser().getId(), userId);

        return ResponseEntity.ok(profileViewDto);
    }

}
