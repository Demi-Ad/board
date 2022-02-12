package com.study.board.web.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSessionData {

    private Long id;

    private String userId;

    private String userNickName;
}
