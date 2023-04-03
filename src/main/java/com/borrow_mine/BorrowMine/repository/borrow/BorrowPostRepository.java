package com.borrow_mine.BorrowMine.repository.borrow;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BorrowPostRepository extends JpaRepository<BorrowPost, Long>, BorrowPostRepositoryCustom{

    @Query("select bp from BorrowPost bp left join fetch bp.member where bp.id = :id")
    Optional<BorrowPost> findBorrowPostByIdFetchMember(@Param("id") Long id);

    @Query("select bp from BorrowPost bp left join fetch bp.member where bp.id = :id")
    Optional<BorrowPost> findBorrowPostByIdWithMember(@Param("id") Long id);
}
