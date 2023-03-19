package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.dto.member.MemberLoginDto;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

//    TODO 비밀번호 그대로 저장하면 안된다!
    @Transactional
    public void join(MemberJoinDto memberJoinDto) {

        validateDuplicateMember(memberJoinDto);

        Member member = new Member(memberJoinDto);
        memberRepository.save(member);
    }

    private void validateDuplicateMember(MemberJoinDto memberJoinDto) {
        validateEmail(memberJoinDto.getEmail());
        validateNickname(memberJoinDto.getNickname());
    }

    private void validateNickname(String nickname) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        if (findMember.isPresent()) {
            throw new IllegalStateException("Member Nickname Duplicate");
        }
    }

    private void validateEmail(String email) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(email);

        if (findMember.isPresent()) {
            throw new IllegalStateException("Member Email Duplicate");
        }
    }

    public Member login(MemberLoginDto memberLoginDto) {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndPassword(memberLoginDto.getEmail(), memberLoginDto.getPassword());
        return findMember.orElseThrow();
    }
}
