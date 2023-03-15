package com.borrow_mine.BorrowMine.dto.borrow;

import lombok.Getter;

@Getter
public class ImageDto {

    private byte[] image;
    private String imageName;

    public ImageDto(byte[] image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }
}
