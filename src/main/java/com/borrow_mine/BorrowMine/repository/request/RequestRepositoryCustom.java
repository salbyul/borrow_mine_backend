package com.borrow_mine.BorrowMine.repository.request;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.request.RequestDto;

import java.util.List;

public interface RequestRepositoryCustom {

    List<RequestDto> getSentRequestDtoListByMember(Member member);

    List<RequestDto> getReceivedRequestDtoListByMember(Member member);
}
