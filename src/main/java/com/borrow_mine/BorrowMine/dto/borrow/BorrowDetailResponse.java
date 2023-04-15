package com.borrow_mine.BorrowMine.dto.borrow;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@Getter
public class BorrowDetailResponse {

    private BorrowDetail borrowDetail;
    private Boolean isBookmark;

    public static BorrowDetailResponse assembleBorrowDetailResponse(BorrowDetail borrowDetail, Boolean isBookmark) {
        BorrowDetailResponse borrowDetailResponse = new BorrowDetailResponse();
        borrowDetailResponse.setBorrowDetail(borrowDetail);
        borrowDetailResponse.setIsBookmark(isBookmark);
        return borrowDetailResponse;
    }
}
