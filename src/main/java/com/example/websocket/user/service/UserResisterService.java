package com.example.websocket.user.service;

import com.example.websocket.user.domain.User;
import com.example.websocket.user.dto.request.UserRegisterDto;
import com.example.websocket.user.exception.ErrorStatus;
import com.example.websocket.user.exception.UserException;
import com.example.websocket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserResisterService { // 회원가입 비즈니스 로직

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long register(UserRegisterDto userRegisterDto) {
        extracted(userRegisterDto.getEmail());

        String password = passwordEncoder.encode(userRegisterDto.getPassword());
        userRegisterDto.encodePassword(password);
        User user = userRegisterDto.toEntity();

        return userRepository.save(user).getId();
    }

    @Transactional
    public Boolean checkDuplicateEmail(String email) { // 이메일 중복체크
        log.info("email: {}", email);
        extracted(email);

        return true;
    }

    private void extracted(String email) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new UserException(ErrorStatus.DUPLICATE_USER);
        }
    }

}
