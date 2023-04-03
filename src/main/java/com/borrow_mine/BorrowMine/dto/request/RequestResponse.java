package com.borrow_mine.BorrowMine.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestResponse {

    private List<RequestDto> requestDtoList;

    public static RequestResponse assembleRequestResponse(List<RequestDto> requestDtoList) {
        return new RequestResponse(requestDtoList);
    }
}
