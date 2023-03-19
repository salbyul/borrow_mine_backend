package com.borrow_mine.BorrowMine.web.controller.member;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.dto.member.MemberLoginDto;
import com.borrow_mine.BorrowMine.jwt.JwtTokenProvider;
import com.borrow_mine.BorrowMine.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    //    TODO ResponseEntity 그대로 던져도 될까?
    @PostMapping("/join")
    public ResponseEntity<String> joinMember(@Valid @RequestBody MemberJoinDto memberJoinDto) {
        memberService.join(memberJoinDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody MemberLoginDto memberLoginDto) {
        Member member = memberService.login(memberLoginDto);
        String token = jwtTokenProvider.createToken(member.getNickname());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("nickname", member.getNickname());
        return ResponseEntity.ok(responseBody);
    }
}
