package com.borrow_mine.BorrowMine.web.controller.comment;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.comment.CommentSaveDto;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.service.CommentService;
import com.borrow_mine.BorrowMine.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final MemberRepository memberRepository;
    private final CommentService commentService;
    private final ReportService reportService;

    @PutMapping("/save")
    public ResponseEntity<String> saveComment(@Valid @RequestBody CommentSaveDto commentSaveDto, HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        System.out.println("nickname = " + nickname);
        Optional<Member> member = memberRepository.findMemberByNickname(nickname);
        commentService.saveComment(commentSaveDto, member.orElseThrow());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/report/{id}")
    public ResponseEntity<Object> report(HttpServletRequest request, @PathVariable("id") Long commentId) {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        reportService.reportComment(commentId, findMember.orElseThrow());
        return ResponseEntity.ok().build();
    }
}
