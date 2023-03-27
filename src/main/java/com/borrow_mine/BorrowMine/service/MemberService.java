package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.dto.member.MemberLoginDto;
import com.borrow_mine.BorrowMine.repository.DenyRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final DenyRepository denyRepository;

//    TODO 비밀번호 그대로 저장하면 안된다!
    @Transactional
    public void join(MemberJoinDto memberJoinDto) {

        validateDuplicateMember(memberJoinDto);

        Member member = new Member(memberJoinDto);
        memberRepository.save(member);
    }

    public Member login(MemberLoginDto memberLoginDto) {
        Optional<Member> findMember = memberRepository.findMemberByEmailAndPassword(memberLoginDto.getEmail(), memberLoginDto.getPassword());
        return findMember.orElseThrow();
    }

    @Transactional
    public void deny(Member from, Member to) {
        Optional<Deny> findDeny = denyRepository.findByFromAndTo(from, to);
        findDeny.ifPresent((d) -> {
            throw new DuplicateRequestException("중복");
        });
        denyRepository.save(Deny.assembleDeny(from, to));
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
}
