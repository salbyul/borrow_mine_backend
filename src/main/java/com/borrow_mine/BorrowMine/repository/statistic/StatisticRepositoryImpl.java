package com.borrow_mine.BorrowMine.repository.statistic;

import com.borrow_mine.BorrowMine.domain.Statistic;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.borrow_mine.BorrowMine.domain.QStatistic.*;

@RequiredArgsConstructor
public class StatisticRepositoryImpl implements StatisticRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Statistic> findOrderByNumber(Integer limit) {
        return queryFactory
                .selectFrom(statistic)
                .orderBy(statistic.number.desc())
                .limit(limit)
                .fetch();
    }
}
