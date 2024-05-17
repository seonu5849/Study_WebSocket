package com.example.websocket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    // WebSocketHandler가 웹 통신을 할 수 있도록 해주는 핸들러이다.
    private final WebSocketHandler webSocketHandler;

    /**
     * 웹소켓 통신을 하기 위해 엔드 포인트 설정
     * 아래 동작을 통해 http 통신이 아닌 websocket 통신을 하여 websocket 핸드쉐이킹을 진행
      */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // endpoint 설정 (/api/v1/chat/{chatId})
        // 이를 통해 ws://localhost:8080/ws/chat 으로 요청이 들어오면 websocket 통신을 진행
        // setAllowedOrigins을 * 으로 설정하여 모든 ip에서 접근할 수 있도록 한다. (모든 cors 요청 허용)
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
