package com.borrow_mine.BorrowMine.web.controller.borrow;

import com.borrow_mine.BorrowMine.exception.ErrorResult;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = BorrowPostController.class)
public class BorrowPostControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateRequestException.class)
    public ErrorResult duplicateReport(DuplicateRequestException e) {
        return new ErrorResult("DUPLICATE", 111);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult illegalState(IllegalStateException e) {
        if (e.getMessage().equals("Deny Error")) {
            return new ErrorResult("Deny Error", 333);
        } else if (e.getMessage().equals("Request Member Error")) {
            return new ErrorResult("Error", 222);
        } else if (e.getMessage().equals("Request State Error")) {
            return new ErrorResult(e.getMessage(), 444);
        } else if (e.getMessage().equals("Request Period Error")) {
            return new ErrorResult(e.getMessage(), 555);
        } else if (e.getMessage().equals("BORROW_POST DELETE ERROR")) {
            return new ErrorResult("BORROW_POST DELETE ERROR", 666);
        }
        return null;
    }
}
