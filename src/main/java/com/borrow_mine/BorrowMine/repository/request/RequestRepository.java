package com.borrow_mine.BorrowMine.repository.request;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.domain.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long>, RequestRepositoryCustom {

    @Query("select r from Request r where r.member = :member and r.borrowPost = :borrowPost and r.state <> com.borrow_mine.BorrowMine.domain.request.State.REFUSE")
    List<Request> findRequestByBorrowPostAndMember(@Param("borrowPost") BorrowPost borrowPost,@Param("member") Member member);

    @Query("select count(r) from Request r where r.borrowPost = :borrowPost and r.state = com.borrow_mine.BorrowMine.domain.request.State.ACCEPT")
    Integer findAcceptRequestByBorrowPost(@Param("borrowPost") BorrowPost borrowPost);

    @Query("select r from Request r left join fetch r.borrowPost where r.id = :id")
    Optional<Request> findRequestById(@Param("id") Long id);
}
