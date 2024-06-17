//package com.example.websocket.chatroom.service;
//
//import com.example.websocket.chatroom.repository.ChatRoomRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@Slf4j
//@SpringBootTest
//class ChatRoomLstServiceTest {
//
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//    @Test
//    public void 채팅방_리스트_출력() {
//        // given
//        Long userId = 1L;
//
//        // when
//        List<Object[]> results = chatRoomRepository.findByLastCommentPerChatRoom(userId);
//
//        for(Object[] result : results) {
//            log.info("result: {}", result);
//        }
//
//        // then
//        Assertions.assertThat(results).isNotNull();
//    }
//}