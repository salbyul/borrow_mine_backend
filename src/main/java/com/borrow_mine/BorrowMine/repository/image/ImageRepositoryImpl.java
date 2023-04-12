package com.borrow_mine.BorrowMine.repository.image;

import com.borrow_mine.BorrowMine.domain.Image;
import com.borrow_mine.BorrowMine.dto.ImageWithoutBorrowPostDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

import static com.borrow_mine.BorrowMine.domain.QImage.*;
import static com.borrow_mine.BorrowMine.domain.borrow.QBorrowPost.*;

@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Image> findImageByBorrowPostIdIn(Collection<Long> ids) {

        return queryFactory
                .select(image)
                .from(image)
                .leftJoin(image.borrowPost, borrowPost).fetchJoin()
                .where(borrowPost.id.in(ids))
                .orderBy(image.borrowPost.id.asc())
                .fetch();
    }

    @Override
    public List<ImageWithoutBorrowPostDto> findImageWithoutDtoByBorrowPostIdIn(Collection<Long> ids) {

        return queryFactory
                .select(Projections.fields(ImageWithoutBorrowPostDto.class, image.name, image.borrowPost.id.as("borrowPostId")))
                .from(image)
                .leftJoin(image.borrowPost, borrowPost)
                .where(image.borrowPost.id.in(ids))
                .orderBy(image.borrowPost.id.asc())
                .fetch();
    }
}
