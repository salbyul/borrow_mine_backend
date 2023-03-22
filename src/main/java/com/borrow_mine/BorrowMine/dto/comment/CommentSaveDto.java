package com.borrow_mine.BorrowMine.dto.comment;


import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CommentSaveDto {

    @NotNull
    private Long borrowPostId;

    @NotEmpty
    private String content;
}
