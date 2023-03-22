package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c left join fetch c.member where c.borrowPost.id = :borrowPostId ")
    List<Comment> findCommentsByBorrowPostId(@Param("borrowPostId") Long borrowPostId);
}
