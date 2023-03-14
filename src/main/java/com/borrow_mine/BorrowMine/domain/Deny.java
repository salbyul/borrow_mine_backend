package com.borrow_mine.BorrowMine.domain;

import com.borrow_mine.BorrowMine.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Deny {

    @Id @GeneratedValue
    @Column(name = "deny_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from")
    private Member from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to")
    private Member to;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
