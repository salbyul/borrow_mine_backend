package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Token;
import com.borrow_mine.BorrowMine.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public void saveToken(String nickname, String accessToken, String refreshToken) {
        Optional<Token> optionalToken = tokenRepository.findTokenByNickname(nickname);
        if (optionalToken.isPresent()) {
            optionalToken.get().updateToken(accessToken, refreshToken);
            return;
        }

        Token token = new Token(nickname, accessToken, refreshToken);
        tokenRepository.save(token);
    }

    @Transactional
    public void updateToken(String newAccessToken, String preRefreshToken, String newRefreshToken) {
        Optional<Token> optionalToken = tokenRepository.findTokenByRefreshToken(preRefreshToken);
        Token token = optionalToken.orElseThrow();
        token.updateToken(newAccessToken, newRefreshToken);
    }
}
