package com.borrow_mine.BorrowMine.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{

    public static final String FORBIDDEN_ACCESS = "Forbidden Access";
    private final int FORBIDDEN_ACCESS_CODE = 444;

    public static final String DUPLICATE_EMAIL = "Member Email Duplicate";
    private final int DUPLICATE_EMAIL_CODE = 101;

    public static final String DUPLICATE_NICKNAME = "Member Nickname Duplicate";
    private final int DUPLICATE_NICKNAME_CODE = 102;

//    비밀번호 변경 시 같은 비밀번호로 변경 불가
    public static final String DUPLICATE_PASSWORD = "Member Password Duplicate";
    private final int DUPLICATE_PASSWORD_CODE = 103;

    public static final String DUPLICATE_BOOKMARK = "Member Bookmark Duplicate";
    private final int DUPLICATE_BOOKMARK_CODE = 104;

    public static final String PASSWORD_ERROR = "Member Password Error";
    private final int PASSWORD_ERROR_CODE = 401;

    public static final String LOGIN_FAILED = "Login failed";
    private final int LOGIN_FAILED_CODE = 400;

    private final String Message;
    private final int code;

    public MemberException(String message) {
        Message = message;
        this.code = getCode(message);
    }

    public MemberException() {
        Message = FORBIDDEN_ACCESS;
        this.code = FORBIDDEN_ACCESS_CODE;
    }

    private int getCode(String message) {
        switch (message) {
            case DUPLICATE_EMAIL:
                return DUPLICATE_EMAIL_CODE;
            case DUPLICATE_NICKNAME:
                return DUPLICATE_NICKNAME_CODE;
            case DUPLICATE_PASSWORD:
                return DUPLICATE_PASSWORD_CODE;
            case PASSWORD_ERROR:
                return PASSWORD_ERROR_CODE;
            case LOGIN_FAILED:
                return LOGIN_FAILED_CODE;
            case DUPLICATE_BOOKMARK:
                return DUPLICATE_BOOKMARK_CODE;
            default:
                return FORBIDDEN_ACCESS_CODE;
        }
    }
}
