package com.borrow_mine.BorrowMine;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        MemberJoinDto memberJoinDto1 = new MemberJoinDto();
        memberJoinDto1.setEmail("asdf@asdf.com");
        memberJoinDto1.setNickname("다파라요");
        memberJoinDto1.setPassword("12341234");
        memberJoinDto1.setAddress("서울시 중구 황학동");
        memberJoinDto1.setZipcode("04567");

        MemberJoinDto memberJoinDto2 = new MemberJoinDto();
        memberJoinDto2.setEmail("2@2.com");
        memberJoinDto2.setNickname("집안을 거덜내자");
        memberJoinDto2.setPassword("asdfasdf");
        memberJoinDto2.setAddress("판교");
        memberJoinDto2.setZipcode("99999");
        memberRepository.save(new Member(memberJoinDto1));
        memberRepository.save(new Member(memberJoinDto2));
    }
}
