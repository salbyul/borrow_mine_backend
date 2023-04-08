package com.borrow_mine.BorrowMine.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.token.access.secret-key}")
    private String AccessSecretKey;
    @Value("${jwt.token.refresh.secret-key}")
    private String refreshSecretKey;


    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    private Key createAccessKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(AccessSecretKey);
        return new SecretKeySpec(secretKeyBytes, algorithm.getJcaName());
    }

    private Key createRefreshKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(refreshSecretKey);
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

        String result = builder.compact();
        return result;
    }

    public String validateToken(String token) {

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
        } catch (ExpiredJwtException e) {
            log.error("EXPIRED TOKEN");
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
