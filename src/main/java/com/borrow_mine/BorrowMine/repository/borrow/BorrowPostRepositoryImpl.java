package com.borrow_mine.BorrowMine.repository.borrow;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.domain.member.QMember;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSmall;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.borrow_mine.BorrowMine.domain.borrow.QBorrowPost.*;
import static com.borrow_mine.BorrowMine.domain.member.QMember.*;

@RequiredArgsConstructor
public class BorrowPostRepositoryImpl implements BorrowPostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BorrowPostSmall> getBorrowPostSmall() {

        return queryFactory
                .select(Projections.fields(BorrowPostSmall.class, borrowPost.createdDate, borrowPost.member.nickname, borrowPost.title, borrowPost.id))
                .from(borrowPost)
                .leftJoin(borrowPost.member, member)
                .orderBy(borrowPost.createdDate.desc())
                .limit(2)
                .fetch();
    }

//    TODO Paging 기능 추가해야함
    @Override
    public List<BorrowPostSmall> getBorrowPostSmallPaging(Integer offset, Integer limit) {
        return queryFactory
                .select(Projections.fields(BorrowPostSmall.class, borrowPost.createdDate, borrowPost.member.nickname, borrowPost.title, borrowPost.id))
                .from(borrowPost)
                .leftJoin(borrowPost.member, member)
                .orderBy(borrowPost.createdDate.desc())
                .limit(8)
                .fetch();
    }

    @Override
    public List<BorrowPostSmall> getBorrowPostSmallByMember(Member member) {
        return queryFactory
                .select(Projections.fields(BorrowPostSmall.class, borrowPost.createdDate, borrowPost.member.nickname, borrowPost.title, borrowPost.id))
                .from(borrowPost)
                .where(borrowPost.member.eq(member))
                .leftJoin(borrowPost.member, QMember.member)
                .orderBy(borrowPost.createdDate.desc())
                .fetch();
    }

    //    %name%가 맞나
    @Override
    public List<String> getProductName(String name) {
        return queryFactory
                .select(borrowPost.product)
                .from(borrowPost)
                .where(borrowPost.product.containsIgnoreCase(name))
                .distinct()
                .limit(6)
                .fetch();
    }

    @Override
    public List<BorrowPost> findForWeek() {
        return queryFactory
                .selectFrom(borrowPost)
                .where(borrowPost.createdDate.after(LocalDateTime.now().minusWeeks(1)))
                .fetch();
    }

    @Override
    public List<BorrowPost> findForMonth() {
        return queryFactory
                .selectFrom(borrowPost)
                .where(borrowPost.createdDate.after(LocalDateTime.now().minusMonths(1)))
                .fetch();
    }
}
