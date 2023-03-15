package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.Image;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByBorrowPostId(Long borrowPostId);

    @Query("select i from Image i join fetch i.borrowPost where i.borrowPost in :borrowPost")
    List<Image> findImageByBorrowPostIdIn(@Param("borrowPost") Collection<BorrowPost> borrowPost);
}
