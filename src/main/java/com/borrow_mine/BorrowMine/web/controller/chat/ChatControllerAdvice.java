package com.borrow_mine.BorrowMine.web.controller.chat;

import com.borrow_mine.BorrowMine.exception.ChatException;
import com.borrow_mine.BorrowMine.exception.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {ChatController.class})
public class ChatControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChatException.class)
    public ErrorResult chatException(ChatException e) {
        return new ErrorResult(e.getMessage(), e.getCode());
    }
}
