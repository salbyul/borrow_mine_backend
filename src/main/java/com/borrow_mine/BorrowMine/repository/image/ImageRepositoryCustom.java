package com.borrow_mine.BorrowMine.repository.image;

import com.borrow_mine.BorrowMine.domain.Image;

import java.util.Collection;
import java.util.List;

public interface ImageRepositoryCustom {

    List<Image> findImageByBorrowPostIdIn(Collection<Long> ids);
}
