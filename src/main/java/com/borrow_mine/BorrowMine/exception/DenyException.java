package com.borrow_mine.BorrowMine.exception;

import lombok.Getter;

@Getter
public class DenyException extends RuntimeException {

    public static final String FORBIDDEN_ACCESS = "FORBIDDEN ACCESS";
    private final int FORBIDDEN_ACCESS_CODE = 444;

    public static final String DUPLICATE_DENY = "DUPLICATE DENY";
    private final int DUPLICATE_DENY_CODE = 101;

    private final String Message;
    private final int code;

    public DenyException(String message) {
        Message = message;
        this.code = getCode(message);
    }

    public DenyException() {
        Message = FORBIDDEN_ACCESS;
        this.code = FORBIDDEN_ACCESS_CODE;
    }

    private int getCode(String message) {
        switch (message) {
            case DUPLICATE_DENY:
                return DUPLICATE_DENY_CODE;
            default:
                return FORBIDDEN_ACCESS_CODE;
        }
    }
}
