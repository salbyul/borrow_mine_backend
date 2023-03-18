package com.borrow_mine.BorrowMine.domain.member;

import com.borrow_mine.BorrowMine.domain.Address;
import com.borrow_mine.BorrowMine.domain.Bookmark;
import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.request.Request;
import com.borrow_mine.BorrowMine.dto.member.MemberJoinDto;
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
    private List<BorrowPost> borrowPosts;

    @OneToMany(mappedBy = "member")
    private List<Request> requests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "from")
    private List<Deny> denies = new ArrayList<>();

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public Member(MemberJoinDto memberJoinDto) {
        this.email = memberJoinDto.getEmail();
        this.password = memberJoinDto.getPassword();
        this.nickname = memberJoinDto.getNickname();
        this.address = new Address(memberJoinDto.getAddress(), memberJoinDto.getZipcode());
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.state = State.ACTIVATE;
    }
}
