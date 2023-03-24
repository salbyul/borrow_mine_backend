package com.borrow_mine.BorrowMine.web.controller.comment;

import com.borrow_mine.BorrowMine.exception.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = CommentController.class)
public class CommentControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResult duplicateReport(RuntimeException e) {
        return new ErrorResult("DUPLICATE REPORT BY COMMENT", 111);
    }
}
