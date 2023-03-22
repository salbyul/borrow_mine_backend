package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Report;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.repository.CommentRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.repository.ReportRepository;
import com.borrow_mine.BorrowMine.repository.borrow.BorrowPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final BorrowPostRepository borrowPostRepository;

    @Transactional
    public void reportBorrowPost(Long borrowPostId, String nickname) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        Optional<BorrowPost> findBorrowPost = borrowPostRepository.findById(borrowPostId);
        reportRepository.save(new Report(findBorrowPost.orElseThrow(), findMember.orElseThrow()));
    }

    @Transactional
    public void reportComment(Long commentId, String nickname) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        Optional<Comment> findComment = commentRepository.findById(commentId);
        reportRepository.save(new Report(findComment.orElseThrow(), findMember.orElseThrow()));
    }
}
