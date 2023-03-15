package com.borrow_mine.BorrowMine.domain;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Image {

    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_id")
    private BorrowPost borrowPost;
}
