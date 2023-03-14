package com.borrow_mine.BorrowMine.domain;

import javax.persistence.*;

@Entity
@Table(name = "borrow_post")
public class BorrowPost {

    @Id @GeneratedValue
    @Column(name = "borrow_post_id")
    private Long id;

    private String title;
}
