package com.study.board.web.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(min = 6, max = 20)
    private String userId;

    @NotEmpty
    @NotNull
    @NotBlank
    @Size(min = 8, max = 20)
    private String userPassword;
}
