package com.borrow_mine.BorrowMine.dto.member;

import com.borrow_mine.BorrowMine.domain.Address;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class MemberJoinDto {


    @NotEmpty
    private String email;

    @NotEmpty
    @Length(min = 8)
    private String password;

    @NotEmpty
    @Length(min = 1, max = 8)
    private String nickname;

    @NotNull
    private Address address;
}
