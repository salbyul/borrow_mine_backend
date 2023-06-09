package com.borrow_mine.BorrowMine.domain.member;

import com.borrow_mine.BorrowMine.domain.Address;
import com.borrow_mine.BorrowMine.domain.Bookmark;
import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.chat.ChatRoom;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.request.Request;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
import com.borrow_mine.BorrowMine.dto.member.MemberModifyDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<BorrowPost> borrowPosts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Request> requests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "from")
    private List<Deny> denies = new ArrayList<>();

    @OneToMany(mappedBy = "from")
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public Member(MemberJoinDto memberJoinDto, String password) {
        this.email = memberJoinDto.getEmail();
        this.password = password;
        this.nickname = memberJoinDto.getNickname();
        this.address = memberJoinDto.getAddress();
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.state = State.ACTIVATE;
    }

    public void modify(MemberModifyDto memberModifyDto) {
        this.nickname = memberModifyDto.getNickname();
        this.address = memberModifyDto.getAddress();
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
