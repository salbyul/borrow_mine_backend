package com.borrow_mine.BorrowMine.domain.comment;

import com.borrow_mine.BorrowMine.domain.Report;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

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
}
