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
        return new ErrorResult("Error", 222);
    }
}
