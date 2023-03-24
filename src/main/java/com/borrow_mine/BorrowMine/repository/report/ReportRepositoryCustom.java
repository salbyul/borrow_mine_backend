package com.borrow_mine.BorrowMine.repository.report;

import com.borrow_mine.BorrowMine.domain.Report;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.member.Member;

import java.util.Optional;

public interface ReportRepositoryCustom {

    Optional<Report> findByMemberAndBorrowPostId(BorrowPost borrowPost, Member member);

    Optional<Report> findByMemberAndComment(Comment comment, Member member);
}
