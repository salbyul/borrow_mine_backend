package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
