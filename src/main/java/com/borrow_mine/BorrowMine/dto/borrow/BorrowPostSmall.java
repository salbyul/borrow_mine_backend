package com.borrow_mine.BorrowMine.dto.borrow;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BorrowPostSmall {

    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private String nickname;
    private List<ImageDto> imageDtoList = new ArrayList<>();

}
