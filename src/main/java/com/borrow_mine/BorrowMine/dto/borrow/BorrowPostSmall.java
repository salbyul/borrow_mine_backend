package com.borrow_mine.BorrowMine.dto.borrow;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BorrowPostSmall {

    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private String nickname;
    private String imageName;
    private byte[] image;

}
