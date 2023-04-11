package com.borrow_mine.BorrowMine.web.controller.borrow;

import com.borrow_mine.BorrowMine.exception.BorrowPostException;
import com.borrow_mine.BorrowMine.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = BorrowPostController.class)
public class BorrowPostControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BorrowPostException.class)
    public ErrorResult illegalState(BorrowPostException e) {
        log.error(e.getMessage());
        return new ErrorResult(e.getMessage(), e.getCode());
    }
}
