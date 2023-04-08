package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("select t from Token t where t.refreshToken = :refreshToken")
    Optional<Token> findTokenByRefreshToken(@Param("refreshToken") String refreshToken);
}
