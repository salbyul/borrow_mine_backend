package com.borrow_mine.BorrowMine.repository.chat;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.chat.ChatDto;

import java.util.List;

public interface ChatRepositoryCustom {

    List<ChatDto> findChatDtoByFromAndTo(Member from, Member to);
}
