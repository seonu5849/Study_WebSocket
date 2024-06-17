package com.example.websocket.chatroom.repository;

import com.example.websocket.chatroom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = "SELECT \n" +
            "    c.chatroom_id, \n" +
            "    c.title, \n" +
            "    c.profile_img, \n" +
            "    (SELECT COUNT(*)\n" +
            "    FROM invite_chat ic2\n" +
            "    WHERE ic2.chatroom_id = c.chatroom_id) AS person_count,\n" +
            "    ct2.create_date,\n" +
            "    ct2.comment\n" +
            "FROM \n" +
            "    chatroom c\n" +
            "JOIN \n" +
            "    invite_chat ic ON c.chatroom_id = ic.chatroom_id\n" +
            "LEFT JOIN \n" +
            "    (SELECT c.chatroom_id, c.create_date, c.comment\n" +
            "\tFROM chat c\n" +
            "\tJOIN (\n" +
            "\t\tSELECT chatroom_id, MAX(create_date) AS max_create_date\n" +
            "\t\tFROM chat\n" +
            "\t\tGROUP BY chatroom_id\n" +
            "\t) max_dates ON c.chatroom_id = max_dates.chatroom_id AND c.create_date = max_dates.max_create_date) ct2 \n" +
            "    ON c.chatroom_id = ct2.chatroom_id\n" +
            "WHERE \n" +
            "    ic.user_id = :userId\n" +
            "GROUP BY \n" +
            "    c.chatroom_id, ct2.comment\n" +
            "ORDER BY \n" +
            "    ct2.create_date DESC", nativeQuery = true)
    List<Object[]> findByUserIdWithChatRoom(@Param("userId") Long userId);
}
