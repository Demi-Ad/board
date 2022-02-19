package com.study.board.web.dto.userdto;

import com.study.board.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {

    @NotEmpty
    @NotNull
    @NotBlank
    @Size(min = 2, max = 10,message = "닉네임은 2글자에서 10글자 사이입니다")
    private String nickName;

    @Email
    private String email;

    public UserEditDto (User user) {
        this.nickName = user.getNickname();
        this.email = user.getEmail();
    }
}
