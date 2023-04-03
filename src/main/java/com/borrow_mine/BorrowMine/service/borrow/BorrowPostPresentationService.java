package com.borrow_mine.BorrowMine.service.borrow;

import com.borrow_mine.BorrowMine.domain.Image;
import com.borrow_mine.BorrowMine.domain.Statistic;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.PopularProductDto;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowListResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSmall;
import com.borrow_mine.BorrowMine.dto.borrow.ImageDto;
import com.borrow_mine.BorrowMine.dto.request.RequestDto;
import com.borrow_mine.BorrowMine.repository.borrow.BorrowPostRepository;
import com.borrow_mine.BorrowMine.repository.image.ImageRepository;
import com.borrow_mine.BorrowMine.repository.request.RequestRepository;
import com.borrow_mine.BorrowMine.repository.statistic.StatisticRepository;
import com.borrow_mine.BorrowMine.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BorrowPostPresentationService {

    private final BorrowPostRepository borrowPostRepository;
    private final RequestRepository requestRepository;
    private final StatisticRepository statisticRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public BorrowListResponse getSmallBorrowPost() {
        List<BorrowPostSmall> borrowPostSmalls = borrowPostRepository.getBorrowPostSmall();
        List<Long> ids = borrowPostSmalls.stream().map(BorrowPostSmall::getId).collect(Collectors.toList());
        List<Image> images = imageRepository.findImageByBorrowPostIdIn(ids);

        addImageDtoList(borrowPostSmalls, images);

        return BorrowListResponse.assembleBorrowSmallList(borrowPostSmalls);
    }

    public BorrowListResponse getSmallBorrowPostPaging(Integer offset) {
        List<BorrowPostSmall> borrowPostSmalls = borrowPostRepository.getBorrowPostSmallPaging(offset, 8);
        List<Long> ids = borrowPostSmalls.stream().map(BorrowPostSmall::getId).collect(Collectors.toList());
        List<Image> images = imageRepository.findImageByBorrowPostIdIn(ids);

        addImageDtoList(borrowPostSmalls, images);


        return BorrowListResponse.assembleBorrowSmallList(borrowPostSmalls, offset);
    }

    public BorrowListResponse getWroteList(Member member) {
        List<BorrowPostSmall> borrowPostSmallList = borrowPostRepository.getBorrowPostSmallByMember(member);
        List<Long> ids = borrowPostSmallList.stream().map(BorrowPostSmall::getId).collect(Collectors.toList());
        List<Image> images = imageRepository.findImageByBorrowPostIdIn(ids);

        addImageDtoList(borrowPostSmallList, images);

        return BorrowListResponse.assembleBorrowSmallList(borrowPostSmallList);
    }

    public List<PopularProductDto> getStatisticLimitTen() {
        List<Statistic> findStatistic = statisticRepository.findOrderByNumber(10);
        return findStatistic.stream().map(s -> new PopularProductDto(s.getNumber(), s.getProduct())).collect(Collectors.toList());
    }

    public List<PopularProductDto> getPopularProductForWeek() {
        List<BorrowPost> findBorrowPosts = borrowPostRepository.findForWeek();
        Map<String, Integer> map = new HashMap<>();
        findBorrowPosts.forEach(b -> {
            map.put(b.getProduct(), map.getOrDefault(b.getProduct(), 0) + 1);
        });
        List<PopularProductDto> result = map.keySet().stream().map(k -> new PopularProductDto(map.get(k), k)).sorted().collect(Collectors.toList());
        while (result.size() > 10) {
            result.remove(10);
        }
        return result;
    }

    public List<PopularProductDto> getPopularProductForMonth() {
        List<BorrowPost> findBorrowPosts = borrowPostRepository.findForMonth();
        Map<String, Integer> map = new HashMap<>();
        findBorrowPosts.forEach(b -> {
            map.put(b.getProduct(), map.getOrDefault(b.getProduct(), 0) + 1);
        });
        List<PopularProductDto> result = map.keySet().stream().map(k -> new PopularProductDto(map.get(k), k)).sorted().collect(Collectors.toList());
        while (result.size() > 10) {
            result.remove(10);
        }
        return result;
    }

    public List<RequestDto> getRequestDtoList(Member member) {
        return requestRepository.getRequestDtoListByMember(member);
    }

    private void addImageDtoList(List<BorrowPostSmall> borrowPostSmalls, List<Image> images) {
        images.forEach(i -> {
            for (BorrowPostSmall borrowPostSmall : borrowPostSmalls) {
                if (i.getBorrowPost().getId().equals(borrowPostSmall.getId())) {
                    try {
                        borrowPostSmall.getImageDtoList().add(new ImageDto(imageService.imageNameToImage(i.getName()), i.getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        });
    }
}
