package com.borrow_mine.BorrowMine.exception;

import lombok.Getter;

@Getter
public class ReportException extends RuntimeException {

    public static final String FORBIDDEN_ACCESS = "Forbidden Access";
    private final int FORBIDDEN_ACCESS_CODE = 444;

//    자신의 게시물에 신고
    public static final String REPORT_ERROR = "Report Error";
    private final int REPORT_ERROR_CODE = 101;

    //    중복 신고
    public static final String DUPLICATE_REPORT_COMMENT = "Duplicate Report By Comment";
    private final int DUPLICATE_REPORT_COMMENT_CODE = 202;

    public static final String DUPLICATE_REPORT_BORROW_POST = "Duplicate Report By BorrowPost";
    private final int DUPLICATE_REPORT_BORROW_POST_CODE = 201;

    private final String message;
    private final int code;

    public ReportException(String message) {
        this.message = message;
        this.code = getCode(message);
    }

    public ReportException() {
        this.message = FORBIDDEN_ACCESS;
        this.code = FORBIDDEN_ACCESS_CODE;
    }

    private int getCode(String message) {
        switch (message) {
            case REPORT_ERROR:
                return REPORT_ERROR_CODE;
            case DUPLICATE_REPORT_COMMENT:
                return DUPLICATE_REPORT_COMMENT_CODE;
            case DUPLICATE_REPORT_BORROW_POST:
                return DUPLICATE_REPORT_BORROW_POST_CODE;
            default:
                return FORBIDDEN_ACCESS_CODE;
        }
    }

}
