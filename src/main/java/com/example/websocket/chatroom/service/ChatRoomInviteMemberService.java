package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.domain.ChatRoom;
import com.example.websocket.chatroom.domain.InviteChat;
import com.example.websocket.chatroom.domain.InviteChatId;
import com.example.websocket.chatroom.exception.ChatRoomException;
import com.example.websocket.chatroom.exception.ErrorStatus;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import com.example.websocket.chatroom.repository.InviteRepository;
import com.example.websocket.chatting.domain.Chat;
import com.example.websocket.chatting.domain.ChatId;
import com.example.websocket.chatting.domain.MessageType;
import com.example.websocket.chatting.dto.response.ChatMessageResponse;
import com.example.websocket.chatting.repository.ChatRepository;
import com.example.websocket.config.utils.TimeFormatUtils;
import com.example.websocket.user.domain.User;
import com.example.websocket.user.exception.UserException;
import com.example.websocket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomInviteMemberService {

    private final InviteRepository inviteRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Transactional
    public List<ChatMessageResponse> inviteMemberForChatRoom(Long chatRoomId, List<Long> userIds) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomException(ErrorStatus.NOT_FOUND_CHATROOM));

        List<User> users = getUsers(userIds);
        return saveEnterMessage(saveInviteMember(users, chatRoom), chatRoom);
    }

    private List<ChatMessageResponse> saveEnterMessage(List<InviteChat> inviteChats, ChatRoom chatRoom) {
        List<ChatMessageResponse> result = null;
        if(!inviteChats.isEmpty()) {
            List<Chat> list = inviteChats.stream()
                    .map(inviteChat -> {
                        Long maxChatId = chatRepository.findMaxChatIdByChatRoomId(chatRoom.getId());
                        Chat chat = Chat.builder()
                                .id(new ChatId(maxChatId + 1, chatRoom.getId()))
                                .chatroom(chatRoom)
                                .user(null)
                                .comment(inviteChat.getUser().getNickname() + "님이 들어왔습니다.")
                                .createDate(TimeFormatUtils.koreaTimeFormat(DateToUtcTimeString(new Date())))
                                .messageType(MessageType.ENTER)
                                .build();
                        return chatRepository.save(chat);
                    }).toList();
            result =  ChatMessageResponse.of(list);
        }
        return result;
    }

    private List<InviteChat> saveInviteMember(List<User> users, ChatRoom chatRoom) {
        return users.stream()
                .map(user -> {
                    InviteChat inviteChat = InviteChat.builder()
                            .inviteChatId(new InviteChatId(user.getId(), chatRoom.getId()))
                            .user(user)
                            .chatroom(chatRoom)
                            .build();
                    return inviteRepository.save(inviteChat);
                })
                .toList();
    }

    private List<User> getUsers(List<Long> userIds) {
        return userIds.stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new UserException(com.example.websocket.user.exception.ErrorStatus.USER_NOT_FOUND)))
                .toList();
    }

    private String DateToUtcTimeString(Date date) {
        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return utcDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

}
