package com.borrow_mine.BorrowMine;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class BorrowMineApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowMineApplication.class, args);
	}

	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}

}
