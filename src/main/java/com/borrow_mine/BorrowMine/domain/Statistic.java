package com.borrow_mine.BorrowMine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// TODO id product로 변경
@Entity
@NoArgsConstructor
@Getter
public class Statistic {

    @Id @GeneratedValue
    @Column(name = "statistic_id")
    private Long id;

    @Column(unique = true)
    private String product;

    private Integer number;

    public Statistic(String product) {
        this.product = product;
        this.number = 1;
    }

    public void addNumber() {
        this.number++;
    }
}
