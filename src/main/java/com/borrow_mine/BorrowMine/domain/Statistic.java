package com.borrow_mine.BorrowMine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Statistic {

    @Id
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
