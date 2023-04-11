package com.borrow_mine.BorrowMine.web.controller;

import com.borrow_mine.BorrowMine.exception.ErrorResult;
import com.borrow_mine.BorrowMine.exception.ReportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.borrow_mine.BorrowMine.web.controller")
public class ReportControllerAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReportException.class)
    public ErrorResult reportException(ReportException e) {
        log.error(e.getMessage());
        return new ErrorResult(e.getMessage(), e.getCode());
    }
}
