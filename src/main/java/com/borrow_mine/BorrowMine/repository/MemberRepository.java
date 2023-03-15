package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
