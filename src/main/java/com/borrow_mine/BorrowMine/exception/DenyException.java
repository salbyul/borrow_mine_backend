package com.borrow_mine.BorrowMine.exception;

import lombok.Getter;

@Getter
public class DenyException extends RuntimeException {

    public static final String forbiddenAccess = "FORBIDDEN ACCESS";
    public static final int forbiddenAccessCode = 444;

    private final String Message;
    private final int code;

    public DenyException(String message, int code) {
        Message = message;
        this.code = code;
    }
}
