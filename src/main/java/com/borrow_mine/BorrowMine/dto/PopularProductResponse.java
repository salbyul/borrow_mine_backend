package com.borrow_mine.BorrowMine.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PopularProductResponse {

    private List<PopularProductDto> productList;
    private int limit;

    public static PopularProductResponse getProductResponse(List<PopularProductDto> list, int limit) {
        return new PopularProductResponse(list, limit);
    }
}
