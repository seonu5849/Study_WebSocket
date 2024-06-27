package com.example.websocket.chatting.handler;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.exception.ChatRoomException;
import com.example.websocket.chatroom.exception.ErrorStatus;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatting.dto.request.ChatMessageRequest;
import com.example.websocket.chatting.service.ChatSaveService;
import com.example.websocket.config.security.domain.PrincipalDetail;
import com.example.websocket.config.utils.TimeFormatUtils;
import com.example.websocket.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.*;

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
    private Set<WebSocketSession> sessions;
    private final Map<Long, Set<WebSocketSession>> chatRoomMap = new HashMap<>();
    private final ChatRoomRepository chatRoomRepository;
    private final ChatSaveService chatSaveService;

    // chatRoomId : {session1, session2}
//    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>(); // 채팅방

    // 웹 소켓 연결 확인
    // afterConnectionEstablished() 메소드를 통해 현재 들어온 웹소켓의 세션을 담아준다.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨.", session.getId());
        URI uri = session.getUri();
        String queryString = uri.getQuery();
        log.info(queryString);

        Long chatRoomId = extractChatRoomIdFromQueryString(queryString);
        // 메모리 상에 채팅방에 대한 세션이 없으면 만들어줌
        // 채팅메시지를 보낼 채팅방을 찾고 해당 채팅방에 속한 세션들에게 메시지를 전송
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NOT_FOUND_CHATROOM));

        // Map 컬렉션에 채팅방 아이디와 채팅방의 세션을 저장하고,
        // 채팅방 아이디에 따라 채팅방 세션에 새로오는 유저의 세션을 저장하여
        // 채팅방의 고유 세션을 유지한다.
        // 이렇게 하면 db에 세션을 저장하지 않고, 메모리로 관리할 수 있다.
        if(chatRoomMap.isEmpty() || !chatRoomMap.containsKey(chatRoom.getId())){
            chatRoomMap.put(chatRoom.getId(), chatRoom.getSessions());
        }

        this.sessions = chatRoomMap.get(chatRoom.getId());
        this.sessions.add(session);
        log.info("webSocketSession: {}", this.sessions);
    }

    // 쿼리스트링에서 chatRoomId 뽑아내기
    private Long extractChatRoomIdFromQueryString(String queryString) {
        String[] params = queryString.split("=");
        if(params.length == 2 && "chatRoomId".equals(params[0])) {
            try {
                return Long.parseLong(params[1]);
            } catch (NumberFormatException e) {
                log.error("Invalid chatRoomId format: {}", params[1]);
            }
        }
        return -1L;
    }

    // 웹 소켓 통신 시 메시지 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        // payload -> chatMessageDto로 변환
        ChatMessageRequest chatMessageDto = mapper.readValue(payload, ChatMessageRequest.class);
        chatMessageDto.setSendTime(TimeFormatUtils.koreaTimeFormat(chatMessageDto.getSendTime()));
        // 유저 아이디 추출
//        Long userId = extractUserId(session);
        log.info("chatMessageDto: {}", chatMessageDto);
        Long chatRoomId = chatMessageDto.getChatRoomId();
        // 메모리 상에 채팅방에 대한 세션이 없으면 만들어줌
        // 채팅메시지를 보낼 채팅방을 찾고 해당 채팅방에 속한 세션들에게 메시지를 전송
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NOT_FOUND_CHATROOM));

        // 만약 채팅방에 참여한 인원이 3명 이상일 경우 실행.
        // 참여한 인원 중 유효하지 않는(방을 나갔거나, 접속 중이지 않는 유저) 세션ID가 있다면 채팅방(ChatRoomSessionMap)에서 제거
        if(this.sessions.size() >= 3) {
            removeClosedSession(this.sessions);
        }

        chatSaveService.saveChatting(chatMessageDto); // db에 저장하고 메시지 젼송
        sendMessageToChatRoom(chatMessageDto, this.sessions);
    }

    private Long extractUserId(WebSocketSession session) throws IOException {
        Authentication authentication = (Authentication) session.getPrincipal();
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof PrincipalDetail) {
                User user = ((PrincipalDetail) principal).getUser();
                return user.getId();
            }
        }

        // PrincipalDetail이 null인 경우 세션을 닫음
        session.close(CloseStatus.NORMAL.withReason("User not authenticated"));
        throw new ChatRoomException(ErrorStatus.NO_ACTIVE_USER_FOUND);
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

    private void sendMessageToChatRoom(ChatMessageRequest chatMessageDto, Set<WebSocketSession> chatRoomSession) {
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
