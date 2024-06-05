package com.example.websocket.profile.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileEditDto {

    private String nickname;
    private String statusMessage;

}
