package com.borrow_mine.BorrowMine.dto.member;

import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
public class ChangePasswordDto {

    @NotEmpty
    @Length(min = 8)
    private String password;

    @NotEmpty
    private String uuid;

}
