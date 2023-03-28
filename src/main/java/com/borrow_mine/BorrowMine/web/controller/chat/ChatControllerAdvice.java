package com.borrow_mine.BorrowMine.web.controller.chat;

import com.borrow_mine.BorrowMine.exception.ErrorResult;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {ChatController.class})
public class ChatControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateRequestException.class)
    public ErrorResult duplicateChatRoom(DuplicateRequestException e) {
        return new ErrorResult("DUPLICATE CHAT_ROOM", 111);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult forbidden(IllegalStateException e) {
        return new ErrorResult("FORBIDDEN ACCESS", 222);
    }
}
