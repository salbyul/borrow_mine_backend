package com.borrow_mine.BorrowMine.web.controller;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetail;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetailResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowListResponse;
import com.borrow_mine.BorrowMine.repository.borrow.BorrowPostRepository;
import com.borrow_mine.BorrowMine.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrow")
public class BorrowPostController {

    private final BorrowPostPresentationService borrowPostPresentationService;
    private final BorrowPostService borrowPostService;
    private final ImageService imageService;
    private final CommentService commentService;
    private final ReportService reportService;

//    TODO 이미지 가져올 때 포스트의 모든 정보를 가져오는 것 손봐야 할 것 같은 느낌
    @GetMapping("/small_list")
    public BorrowListResponse getSmallList() {
        return borrowPostPresentationService.getSmallBorrowPost();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowDetailResponse> getBorrowDetail(@PathVariable("id") Long borrowPostId) {
        BorrowDetail detail = borrowPostService.getDetail(borrowPostId);
        detail.setImageDtoList(imageService.getImageDtoByBorrowPostId(borrowPostId));
        detail.setCommentDtoList(commentService.getCommentDtoList(borrowPostId));
        BorrowDetailResponse response = BorrowDetailResponse.assembleBorrowDetailResponse(detail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/report/{id}")
    public ResponseEntity<Object> report(@PathVariable("id") Long borrowPostId, HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        reportService.reportBorrowPost(borrowPostId, Optional.of(nickname).orElseThrow());
        return ResponseEntity.ok().build();
    }
}
