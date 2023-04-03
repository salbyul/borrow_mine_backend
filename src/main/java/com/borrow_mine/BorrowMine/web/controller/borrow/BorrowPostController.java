package com.borrow_mine.BorrowMine.web.controller.borrow;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.PopularProductDto;
import com.borrow_mine.BorrowMine.dto.PopularProductResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetail;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetailResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowListResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSaveDto;
import com.borrow_mine.BorrowMine.dto.request.RequestDto;
import com.borrow_mine.BorrowMine.dto.request.RequestResponse;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.service.*;
import com.borrow_mine.BorrowMine.service.borrow.BorrowPostPresentationService;
import com.borrow_mine.BorrowMine.service.borrow.BorrowPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrow")
public class BorrowPostController {

    private final MemberRepository memberRepository;
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

    @GetMapping("/list")
    public BorrowListResponse getList() {
        return borrowPostPresentationService.getSmallBorrowPostPaging(0);
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
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        reportService.reportBorrowPost(borrowPostId, findMember.orElseThrow());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestPart BorrowPostSaveDto borrowPostSaveDto, @RequestPart List<MultipartFile> imageList, HttpServletRequest request) throws IOException {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        Long id = borrowPostService.saveBorrowPost(borrowPostSaveDto, imageList, findMember.orElseThrow());
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", id);
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<Map<String, Object>> recommendProductName(@PathVariable String name) {
        List<String> productNames = borrowPostService.recommendProductName(name);
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
    public BorrowListResponse wroteList(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        return borrowPostPresentationService.getWroteList(findMember.orElseThrow());
    }

    @PutMapping("/request")
    public ResponseEntity<Object> borrowRequest(HttpServletRequest request, @RequestParam Long id) {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        borrowPostService.requestBorrow(findMember.orElseThrow(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/request/sent")
    public RequestResponse getSentRequest(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        List<RequestDto> requestDtoList = borrowPostPresentationService.getSentRequestDtoList(findMember.orElseThrow());
        return RequestResponse.assembleRequestResponse(requestDtoList);
    }

    @GetMapping("/request/received")
    public RequestResponse getReceivedRequest(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        List<RequestDto> receivedRequestDtoList = borrowPostPresentationService.getReceivedRequestDtoList(findMember.orElseThrow());
        return RequestResponse.assembleRequestResponse(receivedRequestDtoList);
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
}
