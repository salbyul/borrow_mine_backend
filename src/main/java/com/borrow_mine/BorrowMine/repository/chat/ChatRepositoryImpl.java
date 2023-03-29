package com.borrow_mine.BorrowMine.repository.chat;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.chat.ChatDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.borrow_mine.BorrowMine.domain.chat.QChat.*;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {

    private final JPAQueryFactory queryFactory;

//    TODO LOCK
    @Override
    public List<ChatDto> findChatDtoByFromAndTo(Member from, Member to) {
        return queryFactory
                .select(Projections.fields(ChatDto.class, chat.type, chat.content, chat.imageName, chat.from.nickname, chat.to.nickname, chat.sentTime))
                .from(chat)
                .where(chat.from.eq(from), chat.to.eq(to))
                .fetch();
    }
}
