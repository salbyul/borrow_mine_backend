package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Image;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.dto.borrow.ImageDto;
import com.borrow_mine.BorrowMine.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${image.dir}")
    private String path;

    public List<ImageDto> getImageDtoByBorrowPostId(Long borrowPostId) {
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

    public byte[] imageNameToImage(String imageName) throws IOException {
        return Files.readAllBytes(new File(path + imageName).toPath());
    }

    public void saveImage(List<MultipartFile> imageList, BorrowPost borrowPost) throws IOException {
        for (MultipartFile multipartFile : imageList) {
            transferFile(multipartFile, path, multipartFile.getOriginalFilename());
            imageRepository.save(new Image(multipartFile.getOriginalFilename(), borrowPost));
        }
    }

    private void transferFile(MultipartFile multipartFile, String path, String name) throws IOException {
        multipartFile.transferTo(new File(path, name));
    }

}
