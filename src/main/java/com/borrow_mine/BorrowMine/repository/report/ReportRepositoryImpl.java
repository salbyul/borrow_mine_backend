package com.borrow_mine.BorrowMine.repository.report;

import com.borrow_mine.BorrowMine.domain.QReport;
import com.borrow_mine.BorrowMine.domain.Report;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.borrow_mine.BorrowMine.domain.QReport.*;

@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Report> findByMemberAndBorrowPostId(BorrowPost borrowPost, Member member) {
        Report findReport = queryFactory
                .selectFrom(report)
                .where(report.borrowPost.eq(borrowPost).and(report.member.eq(member)))
                .fetchOne();
        return Optional.ofNullable(findReport);
    }

    @Override
    public Optional<Report> findByMemberAndComment(Comment comment, Member member) {
        Report findReport = queryFactory
                .selectFrom(report)
                .where(report.comment.eq(comment).and(report.member.eq(member)))
                .fetchOne();
        return Optional.ofNullable(findReport);
    }
}
