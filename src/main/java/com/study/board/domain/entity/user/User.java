package com.study.board.domain.entity.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long id;

    @Column(name = "user_id",length = 20)
    private String userId;

    @Column(name = "user_password",length = 20)
    private String password;

    @Column(name = "user_nickname",length = 10)
    private String nickname;

    @Column(name = "user_email")
    private String email;

    @Column(name = "join_date",updatable = false)
    @CreatedDate
    private LocalDateTime joinDate;


    @Builder
    public User(String userId, String password, String nickname,String email) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public void changeInfo(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
