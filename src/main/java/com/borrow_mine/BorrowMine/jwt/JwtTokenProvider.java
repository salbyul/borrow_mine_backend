package com.borrow_mine.BorrowMine.jwt;

import com.borrow_mine.BorrowMine.repository.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

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

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claims)
                .setExpiration(expireTime)
                .signWith(createAccessKey(), algorithm);

        return builder.compact();
    }

//    TODO request를 그냥 받아서 해결해야 할 것 같다. 닉네임을 얻어올 방법도 생각해야하고, AccessToken 이 만료될 경우의 로직을 작성해야 한다.
    public String validateToken(String token, HttpServletRequest request) {

//        String authorization = request.getHeader("Authorization");
//        if (authorization == null) throw new JwtException("JWT TOKEN EXCEPTION");
//
//        String token = authorization.substring(7);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(AccessSecretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return (String) claims.get("nickname");
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
        return null;
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

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claims)
                .setExpiration(expireTime)
                .signWith(createRefreshKey(), algorithm);

        String result = builder.compact();
        return result;
    }
}
