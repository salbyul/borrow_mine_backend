package com.borrow_mine.BorrowMine.dto.member;

import com.borrow_mine.BorrowMine.domain.Address;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Data
public class MemberModifyDto {

    @NotEmpty
    @Length(min = 1, max = 8)
    private String nickname;

    @NotNull
    private Address address;

    @NotEmpty
    @Length(min = 8)
    private String password;
}
