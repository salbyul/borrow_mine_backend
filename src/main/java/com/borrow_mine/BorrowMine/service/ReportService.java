package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Report;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.repository.CommentRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.repository.report.ReportRepository;
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
    public void reportBorrowPost(Long borrowPostId, Member member) {
        Optional<BorrowPost> findBorrowPost = borrowPostRepository.findById(borrowPostId);
        BorrowPost borrowPost = findBorrowPost.orElseThrow();
        validateDuplicateReportBorrowPost(borrowPost, member);
        reportRepository.save(new Report(borrowPost, member));
    }

    @Transactional
    public void reportComment(Long commentId, Member member) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        Comment comment = findComment.orElseThrow();
        validateDuplicateReportComment(comment, member);
        reportRepository.save(new Report(comment, member));
    }

//    TODO RuntimeException 맞나?
    private void validateDuplicateReportBorrowPost(BorrowPost borrowPost, Member member) {
        if (reportRepository.findByMemberAndBorrowPostId(borrowPost, member).isPresent()) {
            throw new RuntimeException("DUPLICATE REPORT BY BORROW_POST");
        }
    }

    private void validateDuplicateReportComment(Comment comment, Member member) {
        if (reportRepository.findByMemberAndComment(comment, member).isPresent()) {
            throw new RuntimeException("DUPLICATE REPORT BY COMMENT");
        }
    }
}
