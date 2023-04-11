package com.borrow_mine.BorrowMine.jwt;

import com.borrow_mine.BorrowMine.exception.ErrorResult;
import com.borrow_mine.BorrowMine.repository.TokenRepository;
import com.borrow_mine.BorrowMine.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final TokenService tokenService;
    private final TokenRepository tokenRepository;

    @Value("${jwt.token.access.secret-key}")
    private String AccessSecretKey;
    @Value("${jwt.token.refresh.secret-key}")
    private String RefreshSecretKey;


    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    private Key createAccessKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(AccessSecretKey);
        return new SecretKeySpec(secretKeyBytes, algorithm.getJcaName());
    }

    private Key createRefreshKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(RefreshSecretKey);
        return new SecretKeySpec(secretKeyBytes, algorithm.getJcaName());
    }

    public String createAccessToken(String nickname) {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put("nickname", nickname);

        Date expireTime = new Date();
//        30분 토큰
        expireTime.setTime(expireTime.getTime() + 30 * 60 * 1000);

        return Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claims)
                .setExpiration(expireTime)
                .signWith(createAccessKey(), algorithm)
                .compact();

    }

    /**
     * 웹소켓에서의 토큰 검증
     *
     * @param token
     * @param request
     * @return
     */
    public void validateToken(String token, HttpServletRequest request) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(AccessSecretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) { // Refresh Token 확인
            log.error("EXPIRED TOKEN");
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    try {
                        Claims body = Jwts.parserBuilder()
                                .setSigningKey(DatatypeConverter.parseBase64Binary(RefreshSecretKey))
                                .build()
                                .parseClaimsJws(refreshToken)
                                .getBody();
//                        Access Token 갱신
                        String nickname = (String) body.get("nickname");
                        String accessToken = createAccessToken(nickname);
                        request.setAttribute("accessToken", accessToken);
                    } catch (Exception exception) { // Refresh Token exception -> 토큰 모두 만료 로직

                    }
                }
            }
        } catch (UnsupportedJwtException e) {
            log.error("UNSUPPORTED TOKEN");
        } catch (IllegalArgumentException e) {
            log.error("ILLEGAL TOKEN");
        } catch (SecurityException | MalformedJwtException exception) {
            log.error("BAD TOKEN");
        }
    }

    public void validateToken(HttpServletRequest request, HttpServletResponse response) {

        String authorization = request.getHeader("Authorization");
        if (authorization == null) throw new JwtException("JWT TOKEN EXCEPTION");

        String accessToken = authorization.substring(7);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(AccessSecretKey))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            String findNickname = (String) claims.get("nickname");
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("nickname")) {
                    if (!findNickname.equals(cookie.getValue())) {
                        // 쿠키 조작 시 처리 로직 ( 토큰 기반 닉네임 변경 )
                        log.error("쿠키 조작");
                        cookie.setValue(findNickname);
                    }
                    log.info("쿠키 확인 완료");
                }
            }
        } catch (ExpiredJwtException e) { // Access Token 확인
            log.error("EXPIRED ACCESS TOKEN");
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    try {
                        Claims body = Jwts.parserBuilder()
                                .setSigningKey(DatatypeConverter.parseBase64Binary(RefreshSecretKey))
                                .build()
                                .parseClaimsJws(refreshToken)
                                .getBody();
//                        Access Token 갱신
                        String nickname = (String) body.get("nickname");
                        String newAccessToken = createAccessToken(nickname);
                        String newRefreshToken = createRefreshToken(nickname);
                        tokenService.updateToken(newAccessToken, refreshToken, newRefreshToken);

                        Cookie newRefreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
                        newRefreshTokenCookie.setMaxAge(60 * 30 * 1000);
                        newRefreshTokenCookie.setDomain("localhost");
                        newRefreshTokenCookie.setPath("/");
                        newRefreshTokenCookie.setHttpOnly(true);

                        Cookie nicknameCookie = new Cookie("nickname", nickname);
                        nicknameCookie.setMaxAge(60 * 30 * 1000);
                        nicknameCookie.setDomain("localhost");
                        nicknameCookie.setPath("/");

                        Cookie newAccessTokenCookie = new Cookie("SKAT", newAccessToken);
                        newAccessTokenCookie.setMaxAge(60 * 30 * 1000);
                        newAccessTokenCookie.setDomain("localhost");
                        newAccessTokenCookie.setPath("/");

                        response.addCookie(newAccessTokenCookie);
                        response.addCookie(nicknameCookie);
                    } catch (Exception exception) { // Refresh Token exception -> 토큰 모두 만료 로직
                        log.error("EXPIRED REFRESH TOKEN");

                        Optional<String> optionalNickname = tokenRepository.findNicknameByRefreshToken(refreshToken);
                        String nickname = optionalNickname.orElseThrow();
                        String newAccessToken = createAccessToken(nickname);
                        String newRefreshToken = createRefreshToken(nickname);
                        tokenService.updateToken(newAccessToken, refreshToken, newRefreshToken);
                        Cookie newAccessTokenCookie = new Cookie("SKAT", newAccessToken);
                        newAccessTokenCookie.setMaxAge(60 * 30 * 1000);
                        newAccessTokenCookie.setDomain("localhost");
                        newAccessTokenCookie.setPath("/");

                        Cookie newRefreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
                        newRefreshTokenCookie.setMaxAge(60 * 30 * 1000);
                        newRefreshTokenCookie.setDomain("localhost");
                        newRefreshTokenCookie.setPath("/");
                        newRefreshTokenCookie.setHttpOnly(true);

                        Cookie nicknameCookie = new Cookie("nickname", nickname);
                        nicknameCookie.setMaxAge(60 * 30 * 1000);
                        nicknameCookie.setDomain("localhost");
                        nicknameCookie.setPath("/");

                        response.addCookie(newAccessTokenCookie);
                        response.addCookie(newRefreshTokenCookie);
                        response.addCookie(nicknameCookie);
                    }
                }
            }
        } catch (UnsupportedJwtException e) {
            log.error("UNSUPPORTED TOKEN");
        } catch (IllegalArgumentException e) {
            log.error("ILLEGAL TOKEN");
        } catch (SecurityException | MalformedJwtException exception) {
            log.error("BAD TOKEN");
        }
    }

    public String createRefreshToken(String nickname) {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put("nickname", nickname);

        Date expireTime = new Date();
//        1시간 토큰
        expireTime.setTime(expireTime.getTime() + 60 * 60 * 1000);

        return Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claims)
                .setExpiration(expireTime)
                .signWith(createRefreshKey(), algorithm)
                .compact();
    }
}
