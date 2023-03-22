package com.borrow_mine.BorrowMine.domain.comment;

import com.borrow_mine.BorrowMine.domain.Report;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.dto.comment.CommentSaveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_post_id")
    private BorrowPost borrowPost;

    @OneToMany(mappedBy = "comment")
    private List<Report> reports = new ArrayList<>();

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Comment(CommentSaveDto commentSaveDto, BorrowPost borrowPost, Member member) {
        this.content = commentSaveDto.getContent();
        this.state = State.ACTIVATE;
        this.borrowPost = borrowPost;
        this.member = member;
        this.createdDate = LocalDateTime.now();
    }

}
