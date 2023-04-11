package com.borrow_mine.BorrowMine.exception;

import lombok.Getter;

@Getter
public class ChatException extends RuntimeException {

    public static final String FORBIDDEN_ACCESS = "FORBIDDEN ACCESS";
    private final int FORBIDDEN_ACCESS_CODE = 444;

    public static final String ONESELF_ERROR = "Oneself Error";
    private final int ONESELF_ERROR_CODE = 101;

    public static final String DUPLICATE_CHAT_ROOM = "Duplicate Chat Room";
    private final int DUPLICATE_CHAT_ROOM_CODE = 101;

    private final String Message;
    private final int code;

    public ChatException(String message) {
        Message = message;
        this.code = getCode(message);
    }

    public ChatException() {
        Message = FORBIDDEN_ACCESS;
        this.code = FORBIDDEN_ACCESS_CODE;
    }

    private int getCode(String message) {
        switch (message) {
            case ONESELF_ERROR:
                return ONESELF_ERROR_CODE;
            case DUPLICATE_CHAT_ROOM:
                return DUPLICATE_CHAT_ROOM_CODE;
            default:
                return FORBIDDEN_ACCESS_CODE;
        }
    }
}
