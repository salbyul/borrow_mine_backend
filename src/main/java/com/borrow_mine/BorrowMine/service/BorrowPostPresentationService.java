package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Image;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowResponse;
import com.borrow_mine.BorrowMine.dto.borrow.BorrowPostSmall;
import com.borrow_mine.BorrowMine.dto.borrow.ImageDto;
import com.borrow_mine.BorrowMine.repository.borrow.BorrowPostRepository;
import com.borrow_mine.BorrowMine.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BorrowPostPresentationService {

    private final BorrowPostRepository borrowPostRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public BorrowResponse getSmallBorrowPost() {
        List<BorrowPostSmall> borrowPostSmalls = borrowPostRepository.getBorrowPostSmall();
        List<Long> ids = borrowPostSmalls.stream().map(BorrowPostSmall::getId).collect(Collectors.toList());
        List<Image> images = imageRepository.findImageByBorrowPostIdIn(ids);

        addImageDtoList(borrowPostSmalls, images);


        return BorrowResponse.assembleBorrowSmallList(borrowPostSmalls);
    }

    private void addImageDtoList(List<BorrowPostSmall> borrowPostSmalls, List<Image> images) {
        images.forEach(i -> {
            for (BorrowPostSmall borrowPostSmall : borrowPostSmalls) {
                if (i.getBorrowPost().getId() == borrowPostSmall.getId()) {
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
