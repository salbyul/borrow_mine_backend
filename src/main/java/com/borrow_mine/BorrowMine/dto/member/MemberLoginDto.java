package com.borrow_mine.BorrowMine.dto.member;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class MemberLoginDto {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
