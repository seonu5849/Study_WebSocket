package com.example.websocket.profile.controller;

import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.profile.dto.request.ProfileEditDto;
import com.example.websocket.profile.dto.response.ProfileViewDto;
import com.example.websocket.profile.service.ProfileEditService;
import com.example.websocket.profile.service.ProfileViewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileApiController {

    private final ProfileViewService profileViewService;
    private final ProfileEditService profileEditService;

    @Operation(summary = "프로필 조회 API")
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileViewDto> profileView(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                      @PathVariable Long userId) {

        ProfileViewDto profileViewDto = profileViewService.profileView(principalDetail.getUser(), userId);

        return ResponseEntity.ok(profileViewDto);
    }

    @Operation(summary = "프로필 정보 수정 API")
    @PatchMapping(value = "/{userId}/edit")
    public ResponseEntity<String> profileEdit(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                              @PathVariable Long userId,
                                              @RequestPart("request") ProfileEditDto profileEditDto,
                                              @RequestPart("profileImg") MultipartFile profileImg) {
        log.debug("profileEdit({}, {}, {}) invoked.", userId, profileEditDto, profileImg);
        if(!(principalDetail.getUser().isMine(userId))){
            log.info("서로 다릅니다.");
        }


        profileEditService.profileEdit(userId, profileEditDto, profileImg);

        return null;
    }

}
