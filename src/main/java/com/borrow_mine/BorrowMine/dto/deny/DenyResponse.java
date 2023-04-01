package com.borrow_mine.BorrowMine.dto.deny;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DenyResponse {

    private List<DenyDto> denyDtoList;
    private Integer count;

    public static DenyResponse assembleDenyResponse(List<DenyDto> denyList, Integer count) {
        return new DenyResponse(denyList, count);
    }
}
