package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByNickname(String nickname);
}
