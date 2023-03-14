package com.borrow_mine.BorrowMine.domain.borrow;

import com.borrow_mine.BorrowMine.domain.Bookmark;
import com.borrow_mine.BorrowMine.domain.Image;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.domain.Period;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.request.Request;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "borrow_post")
public class BorrowPost {

    @Id @GeneratedValue
    @Column(name = "borrow_post_id")
    private Long id;

    private String title;

    private String product;

    private String content;

    private Integer price;

    @Embedded
    private Period period;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "borrowPost")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "borrowPost")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "borrowPost")
    private List<Request> requests = new ArrayList<>();

    @OneToMany(mappedBy = "borrowPost")
    private List<Comment> comments = new ArrayList<>();
}
