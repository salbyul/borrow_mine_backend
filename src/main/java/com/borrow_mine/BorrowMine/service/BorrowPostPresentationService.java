package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.dto.borrow.BorrowResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSmall;
import com.borrow_mine.BorrowMine.repository.BorrowPostRepository;
import com.borrow_mine.BorrowMine.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BorrowPostPresentationService {

    private final BorrowPostRepository borrowPostRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    /**
     * TODO
     * BorrowPostSmall 에 이미지 추가해야함.
     * 쿼리 두개로 해결하고 싶은데 더럽게 안되네 ㅎㅎㅎ
     */
    public BorrowResponse getSmallBorrowPost() {
        List<BorrowPostSmall> borrowPostSmalls = borrowPostRepository.getBorrowPostSmall();

        return BorrowResponse.assembleBorrowSmallList(borrowPostSmalls);
    }
}
