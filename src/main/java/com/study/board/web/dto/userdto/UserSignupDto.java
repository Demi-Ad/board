package com.study.board.web.dto.userdto;

import com.study.board.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.h2.engine.UserBuilder;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDto {

    @NotEmpty
    @NotNull
    @NotBlank
    @Size(min = 6, max = 20)
    private String userId;

    @NotEmpty
    @NotNull
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotEmpty
    @NotNull
    @NotBlank
    @Size(min = 2, max = 10)
    private String nickname;


    @Email
    private String email;


    public User toEntity() {
        return User.builder().userId(this.userId).password(this.password).email(this.email).nickname(this.nickname).build();
    }
}
