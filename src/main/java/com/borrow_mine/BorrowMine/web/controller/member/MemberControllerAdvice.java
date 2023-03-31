package com.borrow_mine.BorrowMine.web.controller.member;

import com.borrow_mine.BorrowMine.exception.ErrorResult;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(basePackageClasses = {MemberController.class})
public class MemberControllerAdvice {

    //    TODO ErrorResult message, code 생각
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult memberException(IllegalStateException e) {

        if (e.getMessage().equals("Member Email Duplicate")) {
            log.error("Member Email Duplicate");
            return new ErrorResult("Member Email Duplicate", 111);
        }

        else if (e.getMessage().equals("Member Nickname Duplicate")) {
            log.error("Member Nickname Duplicate");
            return new ErrorResult("Member Nickname Duplicate", 222);
        }

        else if (e.getMessage().equals("Member Address Null")) {
            log.error("Member Address Null");
            return new ErrorResult("Forbidden Access", 0);
        } else if (e.getMessage().equals("Member Password Error")) {
            log.error("Member Password Error");
            return new ErrorResult("Member Password Error", 444);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult forbiddenAccess(MethodArgumentNotValidException e) {
        log.error("Forbidden Access: [{}]", e.getMessage());
        return new ErrorResult("Forbidden Access", 0);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult failedLogin(NoSuchElementException e) {
        log.error("Login Failed");
        return new ErrorResult("Login failed", 1);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateRequestException.class)
    public ErrorResult duplicateDeny(DuplicateRequestException e) {
        return new ErrorResult("DUPLICATE DENY", 123);
    }
}
