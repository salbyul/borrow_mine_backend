package com.borrow_mine.BorrowMine.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {

        return UUID.randomUUID().toString();
    }

    @GetMapping("/header")
    public ResponseEntity<String> header(HttpServletRequest request) {
        return ResponseEntity.ok((String) request.getAttribute("nickname"));
    }
}
