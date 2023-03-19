package com.borrow_mine.BorrowMine.web.controller;

import com.borrow_mine.BorrowMine.dto.borrow.BorrowResponse;
import com.borrow_mine.BorrowMine.service.BorrowPostPresentationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BorrowPostController {

    private final BorrowPostPresentationService borrowPostPresentationService;

//    TODO 이미지 가져올 때 포스트의 모든 정보를 가져오는 것 손봐야 할 것 같은 느낌
    @GetMapping("/borrow/small_list")
    public BorrowResponse getSmallList() {
        return borrowPostPresentationService.getSmallBorrowPost();
    }
}
