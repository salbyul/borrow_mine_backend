package com.borrow_mine.BorrowMine.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bookmark {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
