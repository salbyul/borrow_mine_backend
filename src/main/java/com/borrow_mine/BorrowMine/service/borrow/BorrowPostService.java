package com.borrow_mine.BorrowMine.service.borrow;

import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.Statistic;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.domain.request.Request;
import com.borrow_mine.BorrowMine.domain.request.State;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetail;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSaveDto;
import com.borrow_mine.BorrowMine.repository.DenyRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.repository.request.RequestRepository;
import com.borrow_mine.BorrowMine.repository.statistic.StatisticRepository;
import com.borrow_mine.BorrowMine.repository.borrow.BorrowPostRepository;
import com.borrow_mine.BorrowMine.service.ImageService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BorrowPostService {

    private final BorrowPostRepository borrowPostRepository;
    private final MemberRepository memberRepository;
    private final DenyRepository denyRepository;
    private final RequestRepository requestRepository;
    private final StatisticRepository statisticRepository;
    private final ImageService imageService;

    public BorrowDetail getDetail(Long borrowPostId) {

        Optional<BorrowPost> findBorrowPost = borrowPostRepository.findBorrowPostByIdFetchMember(borrowPostId);
        BorrowPost borrowPost = findBorrowPost.orElseThrow();
        return new BorrowDetail(borrowPost);
    }

    @Transactional
    public Long saveBorrowPost(BorrowPostSaveDto borrowPostSaveDto, List<MultipartFile> imageList, String nickname) throws IOException {
        Optional<Member> optionalMember = memberRepository.findMemberByNickname(nickname);
        Member findMember = optionalMember.orElseThrow();
        BorrowPost borrowPost = new BorrowPost(borrowPostSaveDto, findMember);
        borrowPostRepository.save(borrowPost);
        imageService.saveImage(imageList, borrowPost);
        String product = borrowPost.getProduct();

        Optional<Statistic> findStatistic = statisticRepository.findByProduct(product);
        if (findStatistic.isEmpty()) {
            statisticRepository.save(new Statistic(product));
        } else {
            Statistic statistic = findStatistic.get();
            statistic.addNumber();
        }

        return borrowPost.getId();
    }

    @Transactional
    public void requestBorrow(String nickname, Long borrowId) {
        Optional<Member> optionalMember = memberRepository.findMemberByNickname(nickname);
        Member findMember = optionalMember.orElseThrow();
        Optional<BorrowPost> findBorrowPost = borrowPostRepository.findBorrowPostByIdWithMember(borrowId);
        BorrowPost borrowPost = findBorrowPost.orElseThrow();
        Optional<Deny> findDeny = denyRepository.findByFromAndTo(borrowPost.getMember(), findMember);
        if (findDeny.isPresent()) {
            throw new IllegalStateException("Deny Error");
        }
        if (borrowPost.getMember() == findMember) {
            throw new IllegalStateException("Request Error");
        }
        List<Request> findRequestList = requestRepository.findRequestByBorrowPostAndMember(borrowPost, findMember);
        if (findRequestList.isEmpty()) {
            requestRepository.save(new Request(null, State.WAIT, borrowPost, findMember, LocalDateTime.now()));
        } else {
            throw new DuplicateRequestException("중복");
        }
    }

    @Transactional
    public void acceptRequestState(Long requestId) {
        Optional<Request> findRequest = requestRepository.findById(requestId);
        Request request = findRequest.orElseThrow();
        if (request.getState().equals(State.WAIT)) {
            request.changeState(State.ACCEPT);
        }
    }

    @Transactional
    public void refuseRequestState(Long requestId) {
        Optional<Request> findRequest = requestRepository.findById(requestId);
        Request request = findRequest.orElseThrow();
        if (request.getState().equals(State.WAIT)) {
            request.changeState(State.REFUSE);
        }
    }

    public List<String> recommendProductName(String name) {
        return borrowPostRepository.getProductName(name);
    }
}
