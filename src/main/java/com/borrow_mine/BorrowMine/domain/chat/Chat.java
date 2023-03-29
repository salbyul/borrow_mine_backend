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

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "sent_time")
    private LocalDateTime sentTime;

    public static Chat assembleChatMessage(Member from, Member to, String message) {
        return new Chat(null, message, null, from, to, Type.TEXT, LocalDateTime.now());
    }

    public static Chat assembleChatImage(Member from, Member to, String imageName) {
        return new Chat(null, null, imageName, from, to, Type.TEXT, LocalDateTime.now());
    }
}
