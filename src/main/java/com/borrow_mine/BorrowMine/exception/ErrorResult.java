package com.borrow_mine.BorrowMine.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO ErrorResult exception 패키지 안에 있는 게 맞나?
@Getter
@AllArgsConstructor
public class ErrorResult {

    private String message;
    private int code;
}
