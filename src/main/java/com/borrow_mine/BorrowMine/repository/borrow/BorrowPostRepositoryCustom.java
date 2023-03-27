package com.borrow_mine.BorrowMine.repository.borrow;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSmall;

import java.util.List;

public interface BorrowPostRepositoryCustom {

    List<BorrowPostSmall> getBorrowPostSmall();

    List<BorrowPostSmall> getBorrowPostSmallPaging(Integer offset, Integer limit);

    List<String> getProductName(String name);

    List<BorrowPost> findForWeek();

    List<BorrowPost> findForMonth();
}
