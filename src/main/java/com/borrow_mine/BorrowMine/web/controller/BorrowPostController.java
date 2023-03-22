package com.borrow_mine.BorrowMine.web.controller;

import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetail;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetailResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowListResponse;
import com.borrow_mine.BorrowMine.dto.borrow.ImageDto;
import com.borrow_mine.BorrowMine.service.BorrowPostPresentationService;
import com.borrow_mine.BorrowMine.service.BorrowPostService;
import com.borrow_mine.BorrowMine.service.CommentService;
import com.borrow_mine.BorrowMine.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BorrowPostController {

    private final BorrowPostPresentationService borrowPostPresentationService;
    private final BorrowPostService borrowPostService;
    private final ImageService imageService;
    private final CommentService commentService;

//    TODO 이미지 가져올 때 포스트의 모든 정보를 가져오는 것 손봐야 할 것 같은 느낌
    @GetMapping("/borrow/small_list")
    public BorrowListResponse getSmallList() {
        return borrowPostPresentationService.getSmallBorrowPost();
    }

    @GetMapping("/borrow/{id}")
    public ResponseEntity<BorrowDetailResponse> getBorrowDetail(@PathVariable("id") Long id) {
        BorrowDetail detail = borrowPostService.getDetail(id);
        detail.setImageDtoList(imageService.getImageDtoByBorrowPostId(id));
        detail.setCommentDtoList(commentService.getCommentDtoList(id));
        BorrowDetailResponse response = BorrowDetailResponse.assembleBorrowDetailResponse(detail);
        return ResponseEntity.ok(response);
    }
}
