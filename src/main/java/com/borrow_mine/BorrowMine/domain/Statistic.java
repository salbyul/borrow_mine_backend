package com.borrow_mine.BorrowMine.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Statistic {

    @Id @GeneratedValue
    @Column(name = "statistic_id")
    private Long id;

    private String product;

    private Integer number;
}
