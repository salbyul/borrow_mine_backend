package com.borrow_mine.BorrowMine.repository.image;

import com.borrow_mine.BorrowMine.domain.Image;
import com.borrow_mine.BorrowMine.dto.ImageWithoutBorrowPostDto;

import java.util.Collection;
import java.util.List;

public interface ImageRepositoryCustom {

    List<Image> findImageByBorrowPostIdIn(Collection<Long> ids);

    List<ImageWithoutBorrowPostDto> findImageWithoutDtoByBorrowPostIdIn(Collection<Long> ids);
}
