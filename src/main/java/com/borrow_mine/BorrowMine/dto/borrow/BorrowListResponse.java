package com.borrow_mine.BorrowMine.dto.borrow;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@Getter
public class BorrowListResponse {

    private List<BorrowPostSmall> borrowPosts;
    private int offset;

    public static BorrowListResponse assembleBorrowSmallList(List<BorrowPostSmall> list) {
        BorrowListResponse borrowResponse = new BorrowListResponse();
        borrowResponse.setBorrowPosts(list);
        return borrowResponse;
    }

    public static BorrowListResponse assembleBorrowSmallList(List<BorrowPostSmall> list, int offset) {
        BorrowListResponse borrowResponse = new BorrowListResponse();
        borrowResponse.setBorrowPosts(list);
        borrowResponse.setOffset(offset);
        return borrowResponse;
    }
}
