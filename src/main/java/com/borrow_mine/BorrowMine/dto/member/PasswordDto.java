package com.borrow_mine.BorrowMine.dto.member;

import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@Getter
public class PasswordDto {

    @NotEmpty
    private String currentPassword;

    @NotEmpty
    @Length(min = 8)
    private String password;
}
