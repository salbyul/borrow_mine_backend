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

    public static BorrowDetailResponse assembleBorrowDetailResponse(BorrowDetail borrowDetail) {
        BorrowDetailResponse borrowDetailResponse = new BorrowDetailResponse();
        borrowDetailResponse.setBorrowDetail(borrowDetail);
        return borrowDetailResponse;
    }
}
