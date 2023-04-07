package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.Address;
import com.borrow_mine.BorrowMine.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByNickname(String nickname);

    Optional<Member> findMemberByEmailAndPassword(String email, String password);

    @Query("select m.nickname from Member m where m.email = :email and m.nickname = :nickname and m.address = :address")
    Optional<String> findMemberWithoutPassword(@Param("email") String email, @Param("nickname") String nickname, @Param("address") Address address);
}
