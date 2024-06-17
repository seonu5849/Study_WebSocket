package com.example.websocket.chatroom.service;

import com.example.websocket.chatroom.dto.response.ChatRoomListDto;
import com.example.websocket.chatroom.repository.ChatRoomQueryRepository;
import com.example.websocket.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomListService {

    private final ChatRoomQueryRepository chatRoomQueryRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public List<ChatRoomListDto> chatRoomListView(Long userId) {
//        List<ChatRoomListDto> chatRoomList = chatRoomQueryRepository.findChatRoomsByUserId(userId);
//
//        // 이 메소드를 통해 각각의 채팅방 별로 최근의 메시지만 보여주도록 한다.
//        List<ChatRoomListDto> filterChatRoom = filterChatRoomList(chatRoomList);
//
//        for(ChatRoomListDto chatRoom : filterChatRoom) {
//            if(chatRoom.getLastCommentDate() != null) {
//                chatRoom.formatLastCommentDate(chatRoom.getLastCommentDate());
//            }
//        }
//
//        return filterChatRoom;
        List<ChatRoomListDto> chatRoomListDtoList = new ArrayList<>();
        List<Object[]> results = chatRoomRepository.findByUserIdWithChatRoom(userId);
        for(Object[] result : results) {
            ChatRoomListDto dto = ChatRoomListDto.builder()
                    .chatRoomId(((Number) result[0]).longValue())
                    .title(((String) result[1]))
                    .profileImg(((String) result[2]))
                    .personCount(((Number) result[3]).longValue())
                    .lastCommentDate(((String) result[4]))
                    .lastComment(((String) result[5]))
                    .build();
            chatRoomListDtoList.add(dto);
        }

        for(ChatRoomListDto chatRoom : chatRoomListDtoList) {
            if(chatRoom.getLastCommentDate() != null) {
                chatRoom.formatLastCommentDate(chatRoom.getLastCommentDate());
            }
        }

        return chatRoomListDtoList;
    }

    private List<ChatRoomListDto> filterChatRoomList(List<ChatRoomListDto> results) {
        return results.stream()
                // chatRoomId로 그룹화
                .collect(Collectors.toMap(ChatRoomListDto::getChatRoomId,
                        Function.identity(),
                        // chatRoomId가 같을 경우 큰 LastCommentDate를 출력
                        BinaryOperator.maxBy(Comparator.comparing(ChatRoomListDto::getLastCommentDate, Comparator.nullsLast(Comparator.naturalOrder())))
                ))
                // 맵의 값들만 추출하여 리스트로 변환
                .values().stream()
                // null 값을 마지막에 두면서 나머지는 내림차순으로 정렬
                .sorted(Comparator.comparing(ChatRoomListDto::getLastCommentDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

}
