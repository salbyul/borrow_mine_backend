package com.borrow_mine.BorrowMine.domain.request;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.borrow.State;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Request {

    @Id @GeneratedValue
    @Column(name = "request_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_post_id")
    private BorrowPost borrowPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
