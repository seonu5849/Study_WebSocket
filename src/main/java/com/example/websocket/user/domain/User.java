package com.example.websocket.user.domain;

import com.example.websocket.config.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 전급권한을 protected로 설정
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
//    @Column(name = "USER_ID")
    private Long id;

    @Column(unique = true)
    private String email;
    private String nickname;
    private String password;
    private String profileImg;
    private String backgroundImg;
    private String statusMessage;

    public void updateProfile(String nickname, String statusMessage, String profileImg) {
        this.nickname = nickname;
        this.statusMessage = statusMessage;
        this.profileImg = profileImg;
    }
}
