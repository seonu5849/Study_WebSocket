package com.example.websocket.config.security;

import com.example.websocket.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationFailureHandler customFailureHandler;

    private final String[] requestMatchers = {
            "/login/**", "/register/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // 정적 리소스 접근 무시 설정 -> css, js 해당
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorization -> authorization
                    .requestMatchers(requestMatchers).permitAll()
                    .anyRequest().authenticated())
            .formLogin(customLogin -> customLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/main", true)
                    .failureHandler(customFailureHandler)
                    .usernameParameter("email")
                    .passwordParameter("password"));


        return http.build();
    }

}
