package com.borrow_mine.BorrowMine.dto.borrow;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@Getter
public class BorrowResponse {

//    TODO 좋은 설계 생각하기
    private List<BorrowPostSmall> borrowPosts;

    public static BorrowResponse assembleBorrowSmallList(List<BorrowPostSmall> list) {
        BorrowResponse borrowResponse = new BorrowResponse();
        borrowResponse.setBorrowPosts(list);
        return borrowResponse;
    }
}
