package com.borrow_mine.BorrowMine.repository.request;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.domain.member.QMember;
import com.borrow_mine.BorrowMine.dto.request.RequestDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.borrow_mine.BorrowMine.domain.borrow.QBorrowPost.*;
import static com.borrow_mine.BorrowMine.domain.request.QRequest.*;

@RequiredArgsConstructor
public class RequestRepositoryImpl implements RequestRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RequestDto> getSentRequestDtoListByMember(Member member) {
        return queryFactory
                .select(Projections.fields(RequestDto.class, request.id.as("requestId"), request.borrowPost.id.as("borrowPostId"), request.borrowPost.product, request.borrowPost.price, request.borrowPost.period, request.createdDate, request.state))
                .from(request)
                .leftJoin(request.borrowPost, borrowPost)
                .where(request.member.eq(member))
                .fetch();
    }

    @Override
    public List<RequestDto> getReceivedRequestDtoListByMember(Member member) {
        return queryFactory
                .select(Projections.fields(RequestDto.class, request.id.as("requestId"), request.borrowPost.id.as("borrowPostId"), request.borrowPost.product, request.borrowPost.price, request.borrowPost.period, request.member.nickname, request.member.id.as("memberId"), request.createdDate, request.state))
                .from(request)
                .leftJoin(request.borrowPost, borrowPost)
                .where(request.borrowPost.member.eq(member))
                .fetch();
    }
}
