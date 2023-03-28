package com.borrow_mine.BorrowMine.domain.chat;

import com.borrow_mine.BorrowMine.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Chat {

    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    private String content;

    @Column(name = "image_name")
    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_from")
    private Member from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_to")
    private Member to;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
