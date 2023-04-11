package com.borrow_mine.BorrowMine.service.borrow;

import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.Statistic;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.borrow.Period;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.domain.request.Request;
import com.borrow_mine.BorrowMine.domain.request.State;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetail;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSaveDto;
import com.borrow_mine.BorrowMine.exception.BorrowPostException;
import com.borrow_mine.BorrowMine.exception.MemberException;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.borrow_mine.BorrowMine.exception.BorrowPostException.*;

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

//    여기서 부터 exception 걸린거 하나하나 확인하고 리액트에서 예외처리 다 바꿔줘야함!!!
    @Transactional
    public BorrowDetail getDetail(Long borrowPostId) {
        Optional<BorrowPost> findBorrowPost = borrowPostRepository.findBorrowPostByIdFetchMember(borrowPostId);
        BorrowPost borrowPost = findBorrowPost.orElseThrow(BorrowPostException::new);
        if (borrowPost.getState() == com.borrow_mine.BorrowMine.domain.borrow.State.DELETE) {
            throw new BorrowPostException(BORROW_POST_DELETE);
        }
        updateState(borrowPost);
        return new BorrowDetail(borrowPost);
    }

    @Transactional
    public Long saveBorrowPost(BorrowPostSaveDto borrowPostSaveDto, List<MultipartFile> imageList, String nickname) throws IOException {
        Optional<Member> optionalMember = memberRepository.findMemberByNickname(nickname);
        Member findMember = optionalMember.orElseThrow(MemberException::new);
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
        Member findMember = optionalMember.orElseThrow(MemberException::new);
        Optional<BorrowPost> findBorrowPost = borrowPostRepository.findBorrowPostByIdFetchMember(borrowId);
        BorrowPost borrowPost = findBorrowPost.orElseThrow(BorrowPostException::new);
        validateRequestBorrowPost(borrowPost, findMember);
        Optional<Deny> findDeny = denyRepository.findByFromAndTo(borrowPost.getMember(), findMember);
        if (findDeny.isPresent()) {
            throw new BorrowPostException(DENY_ERROR);
        }
        List<Request> findRequestList = requestRepository.findRequestByBorrowPostAndMember(borrowPost, findMember);
        if (findRequestList.isEmpty()) {
            requestRepository.save(new Request(null, State.WAIT, borrowPost, findMember, LocalDateTime.now()));
        } else {
            throw new BorrowPostException(DUPLICATE_REPORT);
        }
    }

    @Transactional
    public void acceptRequestState(Long requestId) {
        Optional<Request> findRequest = requestRepository.findRequestById(requestId);
        Request request = findRequest.orElseThrow(BorrowPostException::new);
        if (request.getState().equals(State.WAIT)) {
            request.changeState(State.ACCEPT);
        }
        BorrowPost borrowPost = request.getBorrowPost();
        borrowPost.updateState(com.borrow_mine.BorrowMine.domain.borrow.State.DONE);
    }

    @Transactional
    public void refuseRequestState(Long requestId) {
        Optional<Request> findRequest = requestRepository.findRequestById(requestId);
        Request request = findRequest.orElseThrow(BorrowPostException::new);
        if (request.getState().equals(State.WAIT)) {
            request.changeState(State.REFUSE);
        }
    }

    public List<String> recommendProductName(String name) {
        return borrowPostRepository.getProductName(name);
    }

    @Transactional
    public void deleteBorrowPost(Long borrowPostId, String nickname) {
        Optional<Member> optionalMember = memberRepository.findMemberByNickname(nickname);
        Member findMember = optionalMember.orElseThrow(MemberException::new);
        Optional<BorrowPost> optionalBorrowPost = borrowPostRepository.findBorrowPostByIdFetchMember(borrowPostId);
        BorrowPost findBorrowPost = optionalBorrowPost.orElseThrow(BorrowPostException::new);
        if (findBorrowPost.getMember() != findMember) {
            throw new BorrowPostException(BORROW_POST_DELETE);
        }

        findBorrowPost.updateState(com.borrow_mine.BorrowMine.domain.borrow.State.DELETE);
    }

    private void validateRequestBorrowPost(BorrowPost borrowPost, Member member) {
        if (borrowPost.getMember() == member) {
            throw new BorrowPostException(REQUEST_MEMBER);
        }
        com.borrow_mine.BorrowMine.domain.borrow.State state = borrowPost.getState();
        if (state != com.borrow_mine.BorrowMine.domain.borrow.State.ACTIVATE) {
            throw new BorrowPostException(REQUEST_STATE);
        }
        Period period = borrowPost.getPeriod();
        if (period.getStartDate().isBefore(LocalDate.now())) {
            throw new BorrowPostException(REQUEST_PERIOD);
        }
    }

    private void updateState(BorrowPost borrowPost) {

        if (!(borrowPost.getState() == com.borrow_mine.BorrowMine.domain.borrow.State.ACTIVATE)) return;

        if (borrowPost.getPeriod().getStartDate().isBefore(LocalDate.now())) {
            borrowPost.updateState(com.borrow_mine.BorrowMine.domain.borrow.State.DONE);
        }
    }
}
