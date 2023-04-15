package com.borrow_mine.BorrowMine.domain;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.member.Member;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Bookmark {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_post_id")
    private BorrowPost borrowPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Bookmark(Member member, BorrowPost borrowPost) {
        this.borrowPost = borrowPost;
        this.member = member;
        this.createdDate = LocalDateTime.now();
    }
}
