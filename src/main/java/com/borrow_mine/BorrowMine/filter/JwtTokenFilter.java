package com.borrow_mine.BorrowMine.filter;

import com.borrow_mine.BorrowMine.jwt.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    //    TODO 리프레시 토큰 구현해야댐
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);
        } else if (req.getRequestURI().startsWith("/chat/room/")) {
            String[] split = req.getQueryString().split("=");
            String token = split[1];
            jwtTokenProvider.validateToken(token, req);
            chain.doFilter(request, response);
        } else {
            jwtTokenProvider.validateToken(req, (HttpServletResponse) response);
            chain.doFilter(request, response);
        }
    }
}
