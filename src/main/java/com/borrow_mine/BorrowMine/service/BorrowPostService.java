package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Statistic;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowDetail;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSaveDto;
import com.borrow_mine.BorrowMine.repository.StatisticRepository;
import com.borrow_mine.BorrowMine.repository.borrow.BorrowPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BorrowPostService {

    private final BorrowPostRepository borrowPostRepository;
    private final StatisticRepository statisticRepository;
    private final ImageService imageService;

    public BorrowDetail getDetail(Long borrowPostId) {

        Optional<BorrowPost> findBorrowPost = borrowPostRepository.findBorrowPostByIdFetchMember(borrowPostId);
        BorrowPost borrowPost = findBorrowPost.orElseThrow();
        return new BorrowDetail(borrowPost);
    }

    @Transactional
    public Long saveBorrowPost(BorrowPostSaveDto borrowPostSaveDto, List<MultipartFile> imageList, Member member) throws IOException {
        BorrowPost borrowPost = new BorrowPost(borrowPostSaveDto, member);
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

    public List<String> recommendProductName(String name) {
        return borrowPostRepository.getProductName(name);
    }
}
