package com.borrow_mine.BorrowMine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Token {

    @Id @GeneratedValue
    @Column(name = "token_id")
    private Long id;

    @NotEmpty
    private String nickname;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public Token(String nickname, String accessToken, String refreshToken) {
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.modifiedDate = LocalDateTime.now();
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
        this.modifiedDate = LocalDateTime.now();
    }

    public void updateToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.modifiedDate = LocalDateTime.now();
    }
}
