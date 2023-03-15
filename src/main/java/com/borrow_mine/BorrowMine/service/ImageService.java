package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Image;
import com.borrow_mine.BorrowMine.dto.borrow.ImageDto;
import com.borrow_mine.BorrowMine.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${image.dir}")
    private String path;

    public List<ImageDto> getImageDtoList(Long borrowPostId) {
        List<Image> images = imageRepository.findByBorrowPostId(borrowPostId);
        return images.stream().map(i -> {
            try {
                return new ImageDto(Files.readAllBytes(new File(path + i.getName()).toPath()), i.getName());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }

    public List<ImageDto> getImageDtoByImageName(String imageName) throws IOException {
//        return new ImageDto(Files.readAllBytes(new File(path + imageName).toPath()), imageName);
        return null;
    }

}
