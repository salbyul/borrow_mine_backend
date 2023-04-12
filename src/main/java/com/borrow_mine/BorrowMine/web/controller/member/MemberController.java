package com.borrow_mine.BorrowMine.web.controller.member;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.deny.DenyDto;
import com.borrow_mine.BorrowMine.dto.deny.DenyResponse;
import com.borrow_mine.BorrowMine.dto.member.*;
import com.borrow_mine.BorrowMine.jwt.JwtTokenProvider;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.service.ChangePasswordService;
import com.borrow_mine.BorrowMine.service.MemberService;
import com.borrow_mine.BorrowMine.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChangePasswordService changePasswordService;
    private final TokenService tokenService;

    @PutMapping("/join")
    public ResponseEntity<Object> joinMember(@Valid @RequestBody MemberJoinDto memberJoinDto) {
        memberService.join(memberJoinDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody MemberLoginDto memberLoginDto, HttpServletResponse response) {
        Member member = memberService.login(memberLoginDto);
        String accessToken = jwtTokenProvider.createAccessToken(member.getNickname());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getNickname());

        Cookie nicknameCookie = makeNicknameCookie(member.getNickname());
        Cookie accessTokenCookie = makeAccessTokenCookie(accessToken);
        Cookie refreshTokenCookie = makeRefreshTokenCookie(refreshToken);

        tokenService.saveToken(member.getNickname(), accessToken, refreshToken);

        assembleCookieToResponse(response, nicknameCookie, accessTokenCookie, refreshTokenCookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/deny/{nickname}")
    public ResponseEntity<Object> deny(@CookieValue String nickname, @PathVariable("nickname") String toMemberNickname) {
        if (nickname.equals(toMemberNickname)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        memberService.deny(nickname, toMemberNickname);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deny/list")
    public DenyResponse denyList(@CookieValue String nickname) {
        List<DenyDto> denyList = memberService.getDenyList(nickname);
        return DenyResponse.assembleDenyResponse(denyList, denyList.size());
    }

    @GetMapping("/info")
    public ResponseEntity<MemberInfoDto> memberInfo(@CookieValue String nickname) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        return ResponseEntity.ok(new MemberInfoDto(findMember.orElseThrow()));
    }

    @PostMapping("/info")
    public ResponseEntity<Object> memberModify(@Valid @RequestBody MemberModifyDto memberModifyDto, @CookieValue String nickname, HttpServletResponse response) {
        memberService.modifyMember(nickname, memberModifyDto);
        Cookie nicknameCookie = makeNicknameCookie(memberModifyDto.getNickname());

        String accessToken = jwtTokenProvider.createAccessToken(memberModifyDto.getNickname());
        Cookie accessTokenCookie = makeAccessTokenCookie(accessToken);

        String refreshToken = jwtTokenProvider.createRefreshToken(memberModifyDto.getNickname());
        Cookie refreshTokenCookie = makeRefreshTokenCookie(refreshToken);

        tokenService.updateAllByNickname(memberModifyDto.getNickname(), accessToken, refreshToken, nickname);

        assembleCookieToResponse(response, nicknameCookie, accessTokenCookie, refreshTokenCookie);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deny/delete/{id}")
    public ResponseEntity<Object> removeDeny(@PathVariable(name = "id") Long denyId, @CookieValue String nickname) {
        memberService.removeDeny(denyId, nickname);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/password/forget/validate")
    public ResponseEntity<String> validateMember(@Valid @RequestBody ValidateMemberDto validateMemberDto) {
        String nickname = memberService.validateChangePassword(validateMemberDto);
        String uuid = changePasswordService.save(nickname);
        return ResponseEntity.ok(uuid);
    }

    @PostMapping("/password/forget/change")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        Optional<String> memberNickname = changePasswordService.getMemberNickname(changePasswordDto.getUuid());
        memberService.changePassword(memberNickname.orElseThrow(), changePasswordDto.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/change")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody PasswordDto passwordDto, @CookieValue String nickname) {
        memberService.changePassword(nickname, passwordDto.getCurrentPassword(), passwordDto.getPassword());
        return ResponseEntity.ok().build();
    }

    private void assembleCookieToResponse(HttpServletResponse response, Cookie... cookies) {
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }
    }

    private Cookie makeNicknameCookie(String nickname) {
        Cookie nicknameCookie = new Cookie("nickname", nickname);
        nicknameCookie.setMaxAge(30 * 60); // 30분
        nicknameCookie.setDomain("localhost");
        nicknameCookie.setPath("/");
        return nicknameCookie;
    }

    private Cookie makeAccessTokenCookie(String accessToken) {
        Cookie accessTokenCookie = new Cookie("SKAT", accessToken);
        accessTokenCookie.setMaxAge(30 * 60); // 30분
        accessTokenCookie.setDomain("localhost");
        accessTokenCookie.setPath("/");
        return accessTokenCookie;
    }

    private Cookie makeRefreshTokenCookie(String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(60 * 60); // 1시간
        refreshTokenCookie.setDomain("localhost");
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        return refreshTokenCookie;
    }
}
