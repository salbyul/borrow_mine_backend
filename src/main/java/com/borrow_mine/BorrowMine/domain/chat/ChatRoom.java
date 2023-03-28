package com.borrow_mine.BorrowMine.domain.chat;

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
public class ChatRoom {

    @Id @GeneratedValue
    @Column(name = "chatroom_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_from")
    private Member from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_to")
    private Member to;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public static ChatRoom createChatRoom(Member from, Member to) {
        return new ChatRoom(null, from, to, LocalDateTime.now());
    }
}
