package com.study.board.web.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private String userId;
    private List<UserPublishedPostDto> publishedPostDtoList;

}
