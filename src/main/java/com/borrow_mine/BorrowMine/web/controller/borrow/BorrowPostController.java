package com.borrow_mine.BorrowMine.web.controller.borrow;

import com.borrow_mine.BorrowMine.dto.PopularProductDto;
import com.borrow_mine.BorrowMine.dto.PopularProductResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetail;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetailResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowListResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSaveDto;
import com.borrow_mine.BorrowMine.dto.request.RequestAcceptDto;
import com.borrow_mine.BorrowMine.dto.request.RequestDto;
import com.borrow_mine.BorrowMine.dto.request.RequestResponse;
import com.borrow_mine.BorrowMine.service.*;
import com.borrow_mine.BorrowMine.service.borrow.BorrowPostPresentationService;
import com.borrow_mine.BorrowMine.service.borrow.BorrowPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/borrow")
public class BorrowPostController {

    private final BorrowPostPresentationService borrowPostPresentationService;
    private final BorrowPostService borrowPostService;
    private final BookmarkService bookmarkService;
    private final ImageService imageService;
    private final CommentService commentService;
    private final ReportService reportService;

    @GetMapping("/small_list")
    public BorrowListResponse getSmallList() {
        return borrowPostPresentationService.getSmallBorrowPost();
    }

    @GetMapping("/list")
    public BorrowListResponse getList(@RequestParam Integer offset) {
        return borrowPostPresentationService.getSmallBorrowPostPaging(offset);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowDetailResponse> getBorrowDetail(@PathVariable("id") Long borrowPostId, @CookieValue(required = false) String nickname) {

        BorrowDetail detail = borrowPostService.getDetail(borrowPostId);
        detail.setImageDtoList(imageService.getImageDtoByBorrowPostId(borrowPostId));
        detail.setCommentDtoList(commentService.getCommentDtoList(borrowPostId));

        BorrowDetailResponse response = BorrowDetailResponse.assembleBorrowDetailResponse(detail, bookmarkService.isBookmark(nickname, borrowPostId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookmark")
    public BorrowListResponse getBookmarkList(@CookieValue String nickname) {
        return borrowPostPresentationService.getBookmarkedList(nickname);
    }

    @PostMapping("/bookmark")
    public ResponseEntity<Object> bookmark(@RequestParam(name = "borrow_post_id") Long borrowPostId, @CookieValue String nickname) {
        bookmarkService.saveBookmark(nickname, borrowPostId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bookmark")
    public ResponseEntity<Object> deleteBookmark(@RequestParam(name = "borrow_post_id") Long borrowPostId, @CookieValue String nickname) {
        bookmarkService.deleteBookmark(nickname, borrowPostId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/report/{id}")
    public ResponseEntity<Object> report(@PathVariable("id") Long borrowPostId, @CookieValue String nickname) {
        reportService.reportBorrowPost(borrowPostId, nickname);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestPart BorrowPostSaveDto borrowPostSaveDto, @RequestPart List<MultipartFile> imageList, @CookieValue String nickname) throws IOException {
        Long id = borrowPostService.saveBorrowPost(borrowPostSaveDto, imageList, nickname);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", id);
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<Map<String, Object>> recommendProductName(@PathVariable("name") String input) {
        List<String> productNames = borrowPostService.recommendProductName(input);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("productNames", productNames);
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/popular/until-now")
    public PopularProductResponse popularUntilNow() {
        List<PopularProductDto> list = borrowPostPresentationService.getStatisticLimitTen();
        return PopularProductResponse.getProductResponse(list, 10);
    }

    @GetMapping("/popular/week")
    public PopularProductResponse popularWeek() {
        List<PopularProductDto> list = borrowPostPresentationService.getPopularProductForWeek();
        return PopularProductResponse.getProductResponse(list, 10);
    }

    @GetMapping("/popular/month")
    public PopularProductResponse popularMonth() {
        List<PopularProductDto> list = borrowPostPresentationService.getPopularProductForMonth();
        return PopularProductResponse.getProductResponse(list, 10);
    }

    @GetMapping("/wrote")
    public BorrowListResponse wroteList(@CookieValue String nickname) {
        return borrowPostPresentationService.getWroteList(nickname);
    }

    @PutMapping("/request")
    public ResponseEntity<Object> borrowRequest(@CookieValue String nickname, @RequestParam Long id) {
        borrowPostService.requestBorrow(nickname, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/request/sent")
    public RequestResponse getSentRequest(@CookieValue String nickname) {
        List<RequestDto> requestDtoList = borrowPostPresentationService.getSentRequestDtoList(nickname);
        return RequestResponse.assembleRequestResponse(requestDtoList);
    }

    @GetMapping("/request/received")
    public RequestResponse getReceivedRequest(@CookieValue String nickname) {
        List<RequestDto> receivedRequestDtoList = borrowPostPresentationService.getReceivedRequestDtoList(nickname);
        return RequestResponse.assembleRequestResponse(receivedRequestDtoList);
    }

    @GetMapping("/request/accept")
    public RequestResponse acceptedRequest(@CookieValue String nickname) {
        List<RequestAcceptDto> acceptDtoList = borrowPostPresentationService.getAcceptedDto(nickname);
        return RequestResponse.assembleRequestAcceptedResponse(acceptDtoList);
    }

    @PostMapping("/request/accept")
    public ResponseEntity<Object> acceptRequest(@RequestParam Long id) {
        borrowPostService.acceptRequestState(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request/refuse")
    public ResponseEntity<Object> refuseRequest(@RequestParam Long id) {
        borrowPostService.refuseRequestState(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBorrowPost(@PathVariable(name = "id") Long borrowPostId, @CookieValue String nickname) {
        borrowPostService.deleteBorrowPost(borrowPostId, nickname);
        return ResponseEntity.ok().build();
    }

}
