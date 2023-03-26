package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    Optional<Statistic> findByProduct(String product);
}
