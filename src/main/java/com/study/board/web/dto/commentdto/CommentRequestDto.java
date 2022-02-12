package com.study.board.web.dto.commentdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private Long postId;
    private String userId;
    @NotBlank
    @NotNull
    private String mainText;
}
