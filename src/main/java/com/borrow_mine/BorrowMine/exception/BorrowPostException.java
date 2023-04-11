package com.borrow_mine.BorrowMine.exception;

import lombok.Getter;

@Getter
public class BorrowPostException extends RuntimeException{

    public static final String FORBIDDEN_ACCESS = "Forbidden Access";
    private final int FORBIDDEN_ACCESS_CODE = 444;

    public static final String REQUEST_MEMBER = "Request Member Error";
    private final int REQUEST_MEMBER_CODE = 201;

    public static final String REQUEST_STATE = "Request State Error";
    private final int REQUEST_STATE_CODE = 202;

    public static final String REQUEST_PERIOD = "Request Period Error";
    private final int REQUEST_PERIOD_CODE = 203;

//    다른 회원의 게시물을 삭제하려고 했을 경우
    public static final String BORROW_POST_DELETE = "BorrowPost Delete Error";
    private final int BORROW_POST_DELETE_CODE = 101;

    public static final String DUPLICATE_REPORT = "Duplicate Report";
    private final int DUPLICATE_REPORT_CODE = 301;

    public static final String DENY_ERROR = "Deny Error";
    private final int DENY_ERROR_CODE = 400;

    private final String message;
    private final int code;

    public BorrowPostException(String message) {
        this.message = message;
        this.code = getCode(message);
    }

    public BorrowPostException() {
        this.message = FORBIDDEN_ACCESS;
        this.code = FORBIDDEN_ACCESS_CODE;
    }

    private int getCode(String message) {
        switch (message) {
            case REQUEST_MEMBER:
                return REQUEST_MEMBER_CODE;
            case REQUEST_STATE:
                return REQUEST_STATE_CODE;
            case REQUEST_PERIOD:
                return REQUEST_PERIOD_CODE;
            case BORROW_POST_DELETE:
                return BORROW_POST_DELETE_CODE;
            case DUPLICATE_REPORT:
                return DUPLICATE_REPORT_CODE;
            case DENY_ERROR:
                return DENY_ERROR_CODE;
            default:
                return FORBIDDEN_ACCESS_CODE;
        }
    }
}
