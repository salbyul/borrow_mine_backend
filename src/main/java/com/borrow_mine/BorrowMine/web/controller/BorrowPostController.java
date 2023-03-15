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

    @GetMapping("/borrow/small_list")
    public BorrowResponse getSmallList() {
        return borrowPostPresentationService.getSmallBorrowPost();
    }
}
