package com.borrow_mine.BorrowMine.filter;

import com.borrow_mine.BorrowMine.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    //    TODO 리프레시 토큰 구현해야댐
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
//        String authorization = req.getHeader("Authorization");
//        if (authorization == null) throw new JwtException("JWT TOKEN EXCEPTION");
//
//        String token = authorization.substring(7);
        if (req.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);
        } else {
            jwtTokenProvider.validateToken(req);
            chain.doFilter(request, response);
        }
    }
}
