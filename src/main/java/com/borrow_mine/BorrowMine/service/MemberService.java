package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Address;
import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.deny.DenyDto;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.dto.member.MemberLoginDto;
import com.borrow_mine.BorrowMine.dto.member.MemberModifyDto;
import com.borrow_mine.BorrowMine.dto.member.ValidateMemberDto;
import com.borrow_mine.BorrowMine.exception.DenyException;
import com.borrow_mine.BorrowMine.repository.DenyRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final DenyRepository denyRepository;
    private final EncryptService encryptService;

    //    TODO 비밀번호 그대로 저장하면 안된다!
    @Transactional
    public void join(MemberJoinDto memberJoinDto) {

        validateDuplicateMember(memberJoinDto);

        Member member = new Member(memberJoinDto,encryptService.encrypt(memberJoinDto.getPassword()));
        memberRepository.save(member);
    }

    public Member login(MemberLoginDto memberLoginDto) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(memberLoginDto.getEmail());
        Member member = findMember.orElseThrow();
        if (!encryptService.isMatch(memberLoginDto.getPassword(), member.getPassword())) {
            throw new NoSuchElementException();
        }
        return member;
    }

    @Transactional
    public void deny(Member from, Member to) {
        Optional<Deny> findDeny = denyRepository.findByFromAndTo(from, to);
        findDeny.ifPresent((d) -> {
            throw new DuplicateRequestException("중복");
        });
        denyRepository.save(Deny.assembleDeny(from, to));
    }

    public List<DenyDto> getDenyList(Member member) {
        List<Deny> denyList = denyRepository.findDenyByFrom(member);
        return denyList.stream()
                .map(d -> new DenyDto(d.getTo().getNickname(), d.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeDeny(Long id, String from) {
        Optional<Deny> findDeny = denyRepository.findOneById(id);
        Deny deny = findDeny.orElseThrow();
        if (!deny.getFrom().getNickname().equals(from))
            throw new DenyException(DenyException.forbiddenAccess, DenyException.forbiddenAccessCode);
        denyRepository.delete(deny);
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

    public String validateChangePassword(ValidateMemberDto validateMemberDto) {
        return memberRepository.findMemberWithoutPassword(validateMemberDto.getEmail(), validateMemberDto.getNickname(), validateMemberDto.getAddress()).orElseThrow();
    }

    @Transactional
    public void changePassword(String nickname, String password) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        Member member = findMember.orElseThrow();
        member.changePassword(encryptService.encrypt(password));
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
