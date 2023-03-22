package com.borrow_mine.BorrowMine.domain;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.member.Member;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "report")
public class Report {

    @Id @GeneratedValue
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_post_id")
    private BorrowPost borrowPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Report(BorrowPost borrowPost, Member member) {
        this.borrowPost = borrowPost;
        this.member = member;
        this.createdDate = LocalDateTime.now();
    }

    public Report(Comment comment, Member member) {
        this.comment = comment;
        this.member = member;
        this.createdDate = LocalDateTime.now();
    }
}
