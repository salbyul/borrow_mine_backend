package com.borrow_mine.BorrowMine.repository.statistic;

import com.borrow_mine.BorrowMine.domain.Statistic;

import java.util.List;

public interface StatisticRepositoryCustom {

    List<Statistic> findOrderByNumber(Integer limit);


}
