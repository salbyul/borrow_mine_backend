package com.borrow_mine.BorrowMine.dto.member;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberJoinDto {


    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    @Length(min = 1, max = 8)
    private String nickname;

    @NotEmpty
    private String address;

    @NotEmpty
    private String zipcode;
}
