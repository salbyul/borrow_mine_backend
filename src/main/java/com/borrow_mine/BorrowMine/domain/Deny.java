package com.borrow_mine.BorrowMine.domain;

import com.borrow_mine.BorrowMine.domain.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Deny {

    @Id @GeneratedValue
    @Column(name = "deny_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_from")
    private Member from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_to")
    private Member to;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public static Deny assembleDeny(Member from, Member to) {
        return new Deny(null, from, to, LocalDateTime.now());
    }
}
