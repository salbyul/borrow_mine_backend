package com.borrow_mine.BorrowMine.web.controller.member;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.member.MemberInfoDto;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.dto.member.MemberLoginDto;
import com.borrow_mine.BorrowMine.dto.member.MemberModifyDto;
import com.borrow_mine.BorrowMine.jwt.JwtTokenProvider;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //    TODO ResponseEntity 그대로 던져도 될까?
    @PutMapping("/join")
    public ResponseEntity<String> joinMember(@Valid @RequestBody MemberJoinDto memberJoinDto) {
        memberService.join(memberJoinDto);
        System.out.println("memberJoinDto = " + memberJoinDto);
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

    @PutMapping("/deny/{nickname}")
    public ResponseEntity<Object> deny(HttpServletRequest request, @PathVariable("nickname") String to) {
        String nickname = (String) request.getAttribute("nickname");
        if (nickname.equals(to)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Optional<Member> denyFrom = memberRepository.findMemberByNickname(nickname);
        Optional<Member> denyTo = memberRepository.findMemberByNickname(to);
        memberService.deny(denyFrom.orElseThrow(), denyTo.orElseThrow());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<MemberInfoDto> memberInfo(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        return ResponseEntity.ok(new MemberInfoDto(findMember.orElseThrow()));
    }

//    TODO nickname으로 Member 찾는 모든 메서드는 서비스에서 하자 Transaction내에서 처리해야 할 것 같다 {{ !!!! 토큰 재발급 해야된다. !!!! }}
    @PostMapping("/info")
    public ResponseEntity<Object> memberModify(@Valid @RequestBody MemberModifyDto memberModifyDto, HttpServletRequest request) {
        System.out.println("memberModifyDto = " + memberModifyDto);
        String nickname = (String) request.getAttribute("nickname");
        memberService.modifyMember(nickname, memberModifyDto);
        return ResponseEntity.ok().build();
    }
}
