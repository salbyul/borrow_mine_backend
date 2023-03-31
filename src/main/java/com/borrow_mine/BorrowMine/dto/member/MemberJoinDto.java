package com.borrow_mine.BorrowMine.dto.member;

import com.borrow_mine.BorrowMine.domain.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// TODO Setter 제거 Test Data 생성 시 사용함
@Getter
@Setter
@Data
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
