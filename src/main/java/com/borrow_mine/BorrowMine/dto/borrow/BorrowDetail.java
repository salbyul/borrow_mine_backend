package com.borrow_mine.BorrowMine.dto.borrow;

import com.borrow_mine.BorrowMine.domain.Address;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.borrow.Period;
import com.borrow_mine.BorrowMine.domain.borrow.State;
import com.borrow_mine.BorrowMine.dto.comment.CommentDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BorrowDetail {

    private String title;
    private State state;
    private String nickname;
    private Address address;
    private LocalDateTime createdDate;
    private String content;
    private Integer price;
    private Period period;
    private String product;
    private List<ImageDto> imageDtoList = new ArrayList<>();
    private List<CommentDto> commentDtoList = new ArrayList<>();

    public BorrowDetail(BorrowPost borrowPost) {
        this.title = borrowPost.getTitle();
        this.state = borrowPost.getState();
        this.nickname = borrowPost.getMember().getNickname();
        this.address = borrowPost.getMember().getAddress();
        this.createdDate = borrowPost.getCreatedDate();
        this.content = borrowPost.getContent();
        this.price = borrowPost.getPrice();
        this.period = borrowPost.getPeriod();
        this.product = borrowPost.getProduct();
    }

    public void setImageDtoList(List<ImageDto> imageDtoList) {
        this.imageDtoList = imageDtoList;
    }

    public void setCommentDtoList(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }
}
