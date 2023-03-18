package com.borrow_mine.BorrowMine.web.controller.member;

import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

//    TODO ResponseEntity 그대로 던져도 될까?
    @PostMapping("/join")
    public ResponseEntity joinMember(@Valid @RequestBody MemberJoinDto memberJoinDto) {

        memberService.join(memberJoinDto);

        return ResponseEntity.ok().build();
    }
}
