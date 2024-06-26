package com.example.websocket.chatroom.service;

import com.example.websocket.chatting.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
//@Transactional
@SpringBootTest
class ChatRoomInviteMemberServiceTest {

    @Autowired
    private ChatRoomInviteMemberService chatRoomInviteMemberService;

    @Autowired
    private ChatRepository chatRepository;

    @Test
    public void 채팅에_초대되면_입장메시지_출력() {
        // given
        Long chatRoomId = 1L;
        List<Long> userIds = List.of(4L);

        // when
        Long before = chatRepository.findMaxChatIdByChatRoomId(chatRoomId);
        log.info("before: {}", before);
        chatRoomInviteMemberService.inviteMemberForChatRoom(chatRoomId, userIds);

        // then
        Long after = chatRepository.findMaxChatIdByChatRoomId(chatRoomId);
        log.info("after: {}", after);
    }

    @Disabled
    @Test
    public void dateToZonedDateTime() {
        Date date = new Date();
        log.info("date: {}", date);

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        log.info("zonedDateTime: {}",zonedDateTime);

        String utcTimeString = zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        log.info("utcTimeString: {}", utcTimeString);
    }

}