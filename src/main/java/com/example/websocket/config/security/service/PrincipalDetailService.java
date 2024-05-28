package com.example.websocket.config.security.service;

import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.exception.ErrorStatus;
import com.example.websocket.user.exception.UserException;
import com.example.websocket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("username112: {}", email);

        User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(ErrorStatus.USER_NOT_FOUND.getMessage()));

        return new PrincipalDetail(user);
    }
}
