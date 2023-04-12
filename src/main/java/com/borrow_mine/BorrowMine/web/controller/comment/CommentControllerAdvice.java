package com.borrow_mine.BorrowMine.web.controller.comment;

import com.borrow_mine.BorrowMine.exception.ErrorResponse;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = CommentController.class)
public class CommentControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateRequestException.class)
    public ErrorResponse duplicateReport(DuplicateRequestException e) {
        log.error("ERROR: {}", e.getMessage());
        if (e.getMessage().equals("DUPLICATE REPORT BY COMMENT")) {
            return new ErrorResponse("DUPLICATE REPORT BY COMMENT", 111);
        } else if (e.getMessage().equals("REPORT ERROR")) {
            return new ErrorResponse("REPORT ERROR", 222);
        }
        return null;
    }
}
