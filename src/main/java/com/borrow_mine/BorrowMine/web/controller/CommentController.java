package com.borrow_mine.BorrowMine.web.controller;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.comment.CommentSaveDto;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final MemberRepository memberRepository;
    private final CommentService commentService;

    @PutMapping("/save")
    public ResponseEntity<String> saveComment(@Valid @RequestBody CommentSaveDto commentSaveDto, HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        System.out.println("nickname = " + nickname);
        Optional<Member> member = memberRepository.findMemberByNickname(nickname);
        commentService.saveComment(commentSaveDto, member.orElseThrow());
        return ResponseEntity.ok().build();
    }
}
