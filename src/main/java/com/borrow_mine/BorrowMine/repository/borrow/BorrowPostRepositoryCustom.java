package com.borrow_mine.BorrowMine.repository.borrow;

import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSmall;

import java.util.List;

public interface BorrowPostRepositoryCustom {

    List<BorrowPostSmall> getBorrowPostSmall();
}