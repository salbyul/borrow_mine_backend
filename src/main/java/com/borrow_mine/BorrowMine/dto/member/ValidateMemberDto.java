package com.borrow_mine.BorrowMine.dto.member;

import com.borrow_mine.BorrowMine.domain.Address;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class ValidateMemberDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String nickname;

    @NotNull
    private Address address;
}
