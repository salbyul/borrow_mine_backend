package com.borrow_mine.BorrowMine.repository.image;

import com.borrow_mine.BorrowMine.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {

    List<Image> findByBorrowPostId(Long borrowPostId);

}
