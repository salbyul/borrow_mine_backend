package com.borrow_mine.BorrowMine.dto.comment;

import com.borrow_mine.BorrowMine.domain.comment.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {

    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime createdDate;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
    }
}
