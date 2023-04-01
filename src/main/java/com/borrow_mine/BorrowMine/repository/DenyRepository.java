package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DenyRepository extends JpaRepository<Deny, Long> {

    @Query("select d from Deny d where d.from = :from and d.to = :to")
    Optional<Deny> findByFromAndTo(@Param("from") Member from, @Param("to") Member to);

    @Query("select d from Deny d left join fetch d.to where d.from = :from")
    List<Deny> findDenyByFrom(@Param("from") Member from);

    @Query("select d from Deny d left join fetch d.from where d.id = :id")
    Optional<Deny> findOneById(@Param("id") Long id);
}
