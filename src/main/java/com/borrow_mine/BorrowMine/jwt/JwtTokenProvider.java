package com.borrow_mine.BorrowMine.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    private Key createKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(secretKeyBytes, algorithm.getJcaName());
    }

    public String createToken(String nickname) {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put("nickname", nickname);

        Date expireTime = new Date();
//        TODO 시간 바꿔야함!!
        expireTime.setTime(expireTime.getTime() + 6000 * 30 * 1000);

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claims)
                .setExpiration(expireTime)
                .signWith(createKey(), algorithm);

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
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
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
}
