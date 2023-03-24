package com.borrow_mine.BorrowMine.repository.report;

import com.borrow_mine.BorrowMine.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long>, ReportRepositoryCustom {
}
