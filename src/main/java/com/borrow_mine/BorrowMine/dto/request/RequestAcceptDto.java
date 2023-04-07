package com.borrow_mine.BorrowMine.dto.request;

import com.borrow_mine.BorrowMine.domain.borrow.Period;
import lombok.Getter;

@Getter
public class RequestAcceptDto implements IRequestDto{

    private String product;
    private Integer price;
    private Period period;
    private String memberNickname;
    private Long memberId;
    private Long borrowPostId;
}
