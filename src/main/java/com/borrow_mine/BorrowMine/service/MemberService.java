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
import com.borrow_mine.BorrowMine.exception.MemberException;
import com.borrow_mine.BorrowMine.repository.DenyRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.borrow_mine.BorrowMine.exception.MemberException.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final DenyRepository denyRepository;
    private final EncryptService encryptService;

    @Transactional
    public void join(MemberJoinDto memberJoinDto) {

        validateDuplicateMember(memberJoinDto);

        Member member = new Member(memberJoinDto,encryptService.encrypt(memberJoinDto.getPassword()));
        memberRepository.save(member);
    }

    public Member login(MemberLoginDto memberLoginDto) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(memberLoginDto.getEmail());
        Member member = findMember.orElseThrow(() -> new MemberException(LOGIN_FAILED));
        if (!encryptService.isMatch(memberLoginDto.getPassword(), member.getPassword())) {
            throw new MemberException(LOGIN_FAILED);
        }
        return member;
    }

    @Transactional
    public void deny(String fromMemberNickname, String toMemberNickname) {
        Optional<Member> optionalFromMember = memberRepository.findMemberByNickname(fromMemberNickname);
        Optional<Member> optionalToMember = memberRepository.findMemberByNickname(toMemberNickname);

        Member fromMember = optionalFromMember.orElseThrow(MemberException::new);
        Member toMember = optionalToMember.orElseThrow(MemberException::new);

        Optional<Deny> findDeny = denyRepository.findByFromAndTo(fromMember, toMember);

        findDeny.ifPresent((d) -> {
            throw new DenyException(DenyException.DUPLICATE_DENY);
        });
        denyRepository.save(Deny.assembleDeny(fromMember, toMember));
    }

    public List<DenyDto> getDenyList(String nickname) {
        Optional<Member> optionalMember = memberRepository.findMemberByNickname(nickname);
        List<Deny> denyList = denyRepository.findDenyByFrom(optionalMember.orElseThrow(MemberException::new));
        return denyList.stream()
                .map(d -> new DenyDto(d.getTo().getNickname(), d.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeDeny(Long denyId, String from) {
        Optional<Deny> findDeny = denyRepository.findOneById(denyId);
        Deny deny = findDeny.orElseThrow(DenyException::new);
        if (!deny.getFrom().getNickname().equals(from))
            throw new DenyException();
        denyRepository.delete(deny);
    }

    @Transactional
    public void modifyMember(String nickname, MemberModifyDto memberModifyDto) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        Member member = findMember.orElseThrow(MemberException::new);

//        비밀번호 확인
        if (!encryptService.isMatch(memberModifyDto.getPassword(), member.getPassword()))
            throw new MemberException(PASSWORD_ERROR);

//        닉네임이 변경되었는지 확인하고 변경이 되었다면 닉네임 중복검사를 진행하고 변경
        if (memberModifyDto.getNickname().equals(member.getNickname())) {
            member.modify(memberModifyDto);
        } else {
            Optional<Member> nicknameMember = memberRepository.findMemberByNickname(memberModifyDto.getNickname());
            if (nicknameMember.isPresent()) throw new MemberException(DUPLICATE_NICKNAME);
            member.modify(memberModifyDto);
        }
    }

    public Optional<Deny> findDeny(Member from, Member to) {
        return denyRepository.findByFromAndTo(from, to);
    }

    public String validateChangePassword(ValidateMemberDto validateMemberDto) {
        return memberRepository.findMemberWithoutPassword(validateMemberDto.getEmail(), validateMemberDto.getNickname(), validateMemberDto.getAddress()).orElseThrow(MemberException::new);
    }

//    비밀번호 잊어버렸을 시 사용
    @Transactional
    public void changePassword(String nickname, String password) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        Member member = findMember.orElseThrow(MemberException::new);
        member.changePassword(encryptService.encrypt(password));
    }

//    비밀번호 변경 시 사용
    @Transactional
    public void changePassword(String nickname, String currentPassword, String password) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        Member member = findMember.orElseThrow(MemberException::new);
        if (!encryptService.isMatch(currentPassword, member.getPassword()))
            throw new MemberException(PASSWORD_ERROR);
        if (encryptService.isMatch(password, member.getPassword()))
            throw new MemberException(DUPLICATE_PASSWORD);
        member.changePassword(encryptService.encrypt(password));
    }

    private void validateDuplicateMember(MemberJoinDto memberJoinDto) {
        validateEmail(memberJoinDto.getEmail());
        validateNickname(memberJoinDto.getNickname());
        validateAddress(memberJoinDto.getAddress());
    }

    private void validateAddress(Address address) {
        if (address.getStreet().equals("") || address.getZipcode().equals("")) {
            throw new MemberException();
        }
    }

    private void validateNickname(String nickname) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        if (findMember.isPresent()) {
            throw new MemberException(DUPLICATE_NICKNAME);
        }
    }

    private void validateEmail(String email) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(email);

        if (findMember.isPresent()) {
            throw new MemberException(DUPLICATE_EMAIL);
        }
    }
}
