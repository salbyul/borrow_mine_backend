package com.borrow_mine.BorrowMine.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ImageWithoutBorrowPostDto {

    private String name;

    private Long borrowPostId;
}
