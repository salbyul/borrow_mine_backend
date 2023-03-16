package com.borrow_mine.BorrowMine.repository.borrow;

import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSmall;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
                .limit(8)
                .fetch();
    }
}
