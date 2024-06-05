package com.example.websocket.profile.service;

import com.example.websocket.file.utils.FileUtil;
import com.example.websocket.profile.dto.request.ProfileEditDto;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.exception.ErrorStatus;
import com.example.websocket.user.exception.UserException;
import com.example.websocket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileEditService {

    private final String profileUrl = "../../static/profileImg/";
    private final UserRepository userRepository;

    @Transactional
    public void profileEdit(Long userId, ProfileEditDto profileEditDto, MultipartFile profileImg) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        log.info(profileImg.getOriginalFilename());
        try {
            String fileName = FileUtil.getMultipartFileToFile(profileImg);
            log.info("file: {}", fileName);
            user.updateProfile(profileEditDto.getNickname(), profileEditDto.getStatusMessage(), fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
