package com.borrow_mine.BorrowMine.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinDto {

    private String email;
    private String password;
    private String nickname;
    private String address;
}
