package com.borrow_mine.BorrowMine.web.controller.comment;

import com.borrow_mine.BorrowMine.dto.comment.CommentSaveDto;
import com.borrow_mine.BorrowMine.service.CommentService;
import com.borrow_mine.BorrowMine.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final ReportService reportService;

    @PutMapping("/save")
    public ResponseEntity<Object> saveComment(@Valid @RequestBody CommentSaveDto commentSaveDto, @CookieValue String nickname) {
        commentService.saveComment(commentSaveDto, nickname);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/report/{id}")
    public ResponseEntity<Object> report(@CookieValue String nickname, @PathVariable("id") Long commentId) {
        reportService.reportComment(commentId, nickname);
        return ResponseEntity.ok().build();
    }
}
