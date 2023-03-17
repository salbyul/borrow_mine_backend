package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    void 회원가입_테스트() {
        MemberJoinDto memberJoin1 = new MemberJoinDto();
        MemberJoinDto memberJoin2 = new MemberJoinDto();
        memberJoin1.setEmail("asdf@asdf.com");
        memberJoin1.setAddress("중구 황학동 191");
        memberJoin1.setNickname("coke");
        memberJoin1.setPassword("aa");

        memberService.join(memberJoin1);

        memberJoin2.setEmail("asdf@asdf.com");
        memberJoin2.setAddress("중구 황학동 191");
        memberJoin2.setNickname("kaka");
        memberJoin2.setPassword("aa");

        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(memberJoin2));

        MemberJoinDto memberJoin3 = new MemberJoinDto();
        memberJoin3.setEmail("asdfa@asdf.com");
        memberJoin3.setAddress("중구 황학동 191");
        memberJoin3.setNickname("coke");
        memberJoin3.setPassword("aa");

        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(memberJoin3));

    }
}