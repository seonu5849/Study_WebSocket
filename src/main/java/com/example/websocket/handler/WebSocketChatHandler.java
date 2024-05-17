package com.example.websocket.handler;

import com.example.websocket.domain.ChatRoom;
import com.example.websocket.dto.ChatMessageDto;
import com.example.websocket.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 소켓 통신은 서버와 클라이언트는 1:n 관계를 맺는다.따라서 한개의 서버는 여러개의 클라이언트가 접속 할 수 있다.
 * 서버에는 여러 클라이언트가 보내오는 메시지를 처리하기 위해 핸들러가 필요
 * TextWebSocketHandler를 상속받아 핸들러를 작성
 * 클라이언트로 부터 받은 메시지를 log로 출력 클라이언트로 환영메시지를 보낸다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;

    // 현재 연결된 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final ChatService chatService;

    // chatRoomId : {session1, session2}
//    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>(); // 채팅방

    // 웹 소켓 연결 확인
    // afterConnectionEstablished() 메소드를 통해 현재 들어온 웹소켓의 세션을 담아준다.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨.", session.getId());
        sessions.add(session); // 접속한 세션을 Set에 담아 준다.
    }

    // 웹 소켓 통신 시 메시지 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        // payload -> chatMessageDto로 변환
        ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);
        log.info("chatMessageDto : {}", chatMessageDto.toString());

        String chatRoomId = chatMessageDto.getChatRoomId();
        // 메모리 상에 채팅방에 대한 세션이 없으면 만들어줌
        // 채팅메시지를 보낼 채팅방을 찾고 해당 채팅방에 속한 세션들에게 메시지를 전송
        ChatRoom chatRoom = chatService.findById(chatRoomId);
        Set<WebSocketSession> chatRoomSession = chatRoom.getSessions();

        // message 에 담긴 타입을 확인한다.
        // 이때 message에서 받아온 getType으로 가져온 내용 ChatMessageDto의 MessageType 열거형의 ENTER와 같다면
        // chatRoomSession에 session을 추가해준다.
        if(chatMessageDto.getMessageType().equals(ChatMessageDto.MessageType.ENTER)) {
            chatRoomSession.add(session);
            chatMessageDto.setMessage(chatMessageDto.getSenderId() + "님이 참가하였습니다.");
            sendMessageToChatRoom(chatMessageDto, chatRoomSession);
        }
        // 만약 채팅방에 참여한 인원이 3명 이상일 경우 실행.
        // 참여한 인원 중 유효하지 않는(방을 나갔거나, 접속 중이지 않는 유저) 세션ID가 있다면 채팅방(ChatRoomSessionMap)에서 제거
        if(chatRoomSession.size() >= 3) {
            removeClosedSession(chatRoomSession);
        }

        sendMessageToChatRoom(chatMessageDto, chatRoomSession);
    }

    // afterConnectionClosed() 메소드를 통해 종료된 세션을 확인하고, 세션을 제외한다.
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김.", session.getId());
        sessions.remove(session);
    }

    // ===== 채팅 관련 메소드 =====
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDto));
    }

    private <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
