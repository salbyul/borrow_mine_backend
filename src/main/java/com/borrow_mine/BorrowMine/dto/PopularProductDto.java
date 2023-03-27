package com.borrow_mine.BorrowMine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PopularProductDto implements Comparable<PopularProductDto>{

    private int number;
    private String name;

    @Override
    public int compareTo(PopularProductDto o) {
        return o.number - number;
    }
}
