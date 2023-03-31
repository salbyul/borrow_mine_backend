package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Address;
import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.dto.member.MemberLoginDto;
import com.borrow_mine.BorrowMine.dto.member.MemberModifyDto;
import com.borrow_mine.BorrowMine.repository.DenyRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    @Transactional
    public void modifyMember(String nickname, MemberModifyDto memberModifyDto) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        Member member = findMember.orElseThrow();

        if (!member.getPassword().equals(memberModifyDto.getPassword()))
            throw new IllegalStateException("Member Password Error");

        if (memberModifyDto.getNickname().equals(member.getNickname())) {
            member.modify(memberModifyDto);
        } else {
            Optional<Member> nicknameMember = memberRepository.findMemberByNickname(memberModifyDto.getNickname());
            if (nicknameMember.isPresent()) throw new IllegalStateException("Member Nickname Duplicate");
            member.modify(memberModifyDto);
        }

    }

    public Optional<Deny> findDeny(Member from, Member to) {
        return denyRepository.findByFromAndTo(from, to);
    }

    private void validateDuplicateMember(MemberJoinDto memberJoinDto) {
        validateEmail(memberJoinDto.getEmail());
        validateNickname(memberJoinDto.getNickname());
        validateAddress(memberJoinDto.getAddress());
    }

    private void validateAddress(Address address) {
        if (address.getStreet().equals("") || address.getZipcode().equals("")) {
            throw new IllegalStateException("Member Address Null");
        }
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
