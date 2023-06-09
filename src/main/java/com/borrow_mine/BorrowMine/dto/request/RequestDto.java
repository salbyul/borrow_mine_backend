package com.borrow_mine.BorrowMine.dto.request;

import com.borrow_mine.BorrowMine.domain.borrow.Period;
import com.borrow_mine.BorrowMine.domain.request.State;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RequestDto implements IRequestDto{

    private Long requestId;
    private Long borrowPostId;
    private String product;
    private Integer price;
    private Period period;
    private String nickname;
    private Long memberId;
    private LocalDateTime createdDate;
    private State state;
}
