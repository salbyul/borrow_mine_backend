package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Report;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.exception.BorrowPostException;
import com.borrow_mine.BorrowMine.exception.ReportException;
import com.borrow_mine.BorrowMine.exception.MemberException;
import com.borrow_mine.BorrowMine.repository.CommentRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.repository.report.ReportRepository;
import com.borrow_mine.BorrowMine.repository.borrow.BorrowPostRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.borrow_mine.BorrowMine.exception.ReportException.*;

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
        Optional<Member> optionalMember = memberRepository.findMemberByNickname(nickname);
        Member findMember = optionalMember.orElseThrow(MemberException::new);
        Optional<BorrowPost> findBorrowPost = borrowPostRepository.findById(borrowPostId);
        BorrowPost borrowPost = findBorrowPost.orElseThrow(BorrowPostException::new);
        validateDuplicateReportBorrowPost(borrowPost, findMember);
        reportRepository.save(new Report(borrowPost, findMember));
    }

    @Transactional
    public void reportComment(Long commentId, String nickname) {
        Optional<Member> optionalMember = memberRepository.findMemberByNickname(nickname);
        Member findMember = optionalMember.orElseThrow(MemberException::new);
        Optional<Comment> findComment = commentRepository.findById(commentId);
        Comment comment = findComment.orElseThrow(MemberException::new); // CommentException 생기면 변경
        validateDuplicateReportComment(comment, findMember);
        reportRepository.save(new Report(comment, findMember));
    }

    private void validateDuplicateReportBorrowPost(BorrowPost borrowPost, Member member) {
        if (borrowPost.getMember() == member) {
            throw new ReportException(REPORT_ERROR);
        }
        if (reportRepository.findByMemberAndBorrowPostId(borrowPost, member).isPresent()) {
            throw new ReportException(DUPLICATE_REPORT_BORROW_POST);
        }
    }

    private void validateDuplicateReportComment(Comment comment, Member member) {
        Member findMember = comment.getMember();
        if (findMember == member) {
            throw new ReportException(REPORT_ERROR);
        }
        Optional<Report> optionalReport = reportRepository.findByMemberAndComment(comment, member);
        if (optionalReport.isPresent()) {
            throw new ReportException(DUPLICATE_REPORT_COMMENT);
        }
    }
}
