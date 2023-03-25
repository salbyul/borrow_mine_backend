package com.borrow_mine.BorrowMine.dto.borrow;

import lombok.Getter;

@Getter
public class BorrowPostSaveDto {

    private String title;
    private Integer price;
    private String product;
    private String startDate;
    private String endDate;
    private String content;
}
