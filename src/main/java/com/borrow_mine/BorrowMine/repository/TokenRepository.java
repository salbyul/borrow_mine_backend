package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findTokenByRefreshToken(String refreshToken);

    Optional<Token> findTokenByNickname(String nickname);

    @Query("select t.nickname from Token t where t.refreshToken = :refreshToken")
    Optional<String> findNicknameByRefreshToken(@Param("refreshToken") String refreshToken);
}
