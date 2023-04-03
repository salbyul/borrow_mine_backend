package com.borrow_mine.BorrowMine;

import com.borrow_mine.BorrowMine.filter.JwtTokenFilter;
import com.borrow_mine.BorrowMine.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .maxAge(3600);
    }

//    TODO mapping 설정
    @Bean
    public FilterRegistrationBean<Filter> jwtTokenFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtTokenFilter(jwtTokenProvider));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/header",
//                Comment
                "/comment/save", "/comment/report/*",
//                Borrow
                "/borrow/report/*", "/borrow/create", "/borrow/wrote", "/borrow/request/*",
//                Member
                "/member/deny/*",
                "/member/info/*",
//                Chat
                "/chat/*");
        return filterRegistrationBean;
    }
}
