package com.borrow_mine.BorrowMine.web.controller.member;

import com.borrow_mine.BorrowMine.exception.DenyException;
import com.borrow_mine.BorrowMine.exception.ErrorResult;
import com.borrow_mine.BorrowMine.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public ErrorResult memberException(MemberException e) {
        log.error(e.getMessage());
        return new ErrorResult(e.getMessage(), e.getCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DenyException.class)
    public ErrorResult denyException(DenyException e) {
        log.error(e.getMessage());

        return new ErrorResult(e.getMessage(), e.getCode());
    }

}
