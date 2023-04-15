package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("select b from Bookmark b where b.member.nickname = :nickname and b.borrowPost.id = :borrowPostId")
    Optional<Bookmark> findByMemberNicknameAndBorrowPostId(@Param("nickname") String nickname, @Param("borrowPostId") Long borrowPostId);
}
