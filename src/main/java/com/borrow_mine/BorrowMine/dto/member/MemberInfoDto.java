package com.borrow_mine.BorrowMine.dto.member;

import com.borrow_mine.BorrowMine.domain.Address;
import com.borrow_mine.BorrowMine.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberInfoDto {

    private String email;
    private String nickname;
    private Address address;

    public MemberInfoDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.address = member.getAddress();
    }
}
