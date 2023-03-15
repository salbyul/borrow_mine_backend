package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowPostRepository extends JpaRepository<BorrowPost, Long>, BorrowPostRepositoryCustom{

}
